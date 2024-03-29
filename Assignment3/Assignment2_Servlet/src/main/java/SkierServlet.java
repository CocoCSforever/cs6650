import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import dbManager.LiftRideDao;
import java.util.logging.Level;
import java.util.logging.Logger;

import RMQ.RMQChannelFactory;
import RMQ.RMQChannelPool;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import db.LiftRide;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    // Number of threads to deploy in each test
    private static final int NUM_THREADS = 200;
    // Number of messages to publish in each thread
    private static final int NUM_ITERATIONS = 100;
    // Number of channels to add to pools
    private static final int NUM_CHANS = 80;
    // RMQ broker machine
    private static final String SERVER = "54.213.139.161";
    // test queue name
    private static final String QUEUE_NAME = "yjQueue";
    private LiftRideDao liftRideDao = new LiftRideDao();

    private static RMQChannelPool channelPool;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);

    public SkierServlet() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER);
        factory.setUsername("a");
        factory.setPassword("a");
        Connection conn = factory.newConnection();
        channelPool = new RMQChannelPool(NUM_CHANS, new RMQChannelFactory(conn));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing parameters");
            return;
        }

        // validate url path/check input value and return the response status code
        // /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
        String[] urlParts = urlPath.split("/");
        if (urlParts.length == 7 && urlParts[1].equals("seasons") && urlParts[3].equals("days")
                && urlParts[5].equals("skiers")) {

            // Extract values from the path
            String skierID = urlParts[6];
            String resortID = urlParts[0];
            String seasonID = urlParts[2];
            String dayID = urlParts[4];

            // TODO: fetch liftRide object
//            LiftRide liftRide = new LiftRide(skierID, resortID, null, seasonID, dayID, null);
            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("It works!"+urlPath);
        }else{
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isUrlValid(String[] urlParts) {
        // TODO: validate the request url path according to the API spec
        // urlPath  = "/1/seasons/2019/day/1/skier/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
        if (urlParts.length == 8 && urlParts[2].equals("seasons") && urlParts[4].equals("days")
                && urlParts[6].equals("skiers")){
            return true;
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        String urlPath = req.getPathInfo();
        System.out.println(urlPath);

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing parameters");
            return;
        }

        // /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
        String[] urlParts = urlPath.split("/");

        // validate url path/check input value and return the response status code
        if(!isUrlValid(urlParts)){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("Invalid URL Path parameters: " + urlPath);
            return;
        }

        try {
            // Extract values from the path
            String skierID = urlParts[7];
            String resortID = urlParts[1];
            String seasonID = urlParts[3];
            String dayID = urlParts[5];

            // Extract values from req.body
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }

            // Parse JSON data into LiftRide object
            Gson gson = new Gson();
            LiftRide liftRide = gson.fromJson(sb.toString(), LiftRide.class);
            // Set additional parameters from the path
            liftRide.setSkierID(skierID.trim());  // as the last param, it has trailing /n
            liftRide.setResortID(resortID);
            liftRide.setSeasonID(seasonID);
            liftRide.setDayID(dayID);
//            liftRideDao.createLiftRide(liftRide);

//                System.out.println("Received data: " + liftRide);
            threadPool.submit(() -> sendMessageToBroker(gson.toJson(liftRide)));
            res.setStatus(HttpServletResponse.SC_CREATED);
            res.getWriter().write(liftRide.toString());
        }catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad URL path parameters: " + urlPath + e.getMessage());
            e.printStackTrace();
        }
    }

    // publish liftRide Info to a remote queue
    protected static void sendMessageToBroker(String liftRideString){
        try {
            Channel channel;
            // get a channel from the pool
            channel = channelPool.borrowObject();

            // publish message
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            byte[] payLoad = liftRideString.getBytes();
            channel.basicPublish("", QUEUE_NAME, null, payLoad);

            // return channel to the pool
            channelPool.returnObject(channel);
        } catch (IOException ex) {

        } catch (Exception ex) {

        }
    }
}
