import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;
import db.SeasonVertical;
import dbManager.LiftRideDao;

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
    private static final String SERVER = "52.41.209.209";
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
//        res.setContentType("text/plain");
        res.setContentType("application/json");
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

        // validate url path/check input value and return the response status code
        if(!isUrlValid(urlParts, false)){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("Invalid URL Path parameters: " + urlPath);
            return;
        }

        try {
            if(urlParts.length == 8){
                String resortID = urlParts[1];
                String seasonID = urlParts[3];
                String dayID = urlParts[5];
                String skierID = urlParts[7];

                int verticals = liftRideDao.getVerticalsForSkier(Integer.valueOf(resortID), Integer.valueOf(seasonID), Integer.valueOf(dayID), Integer.valueOf(skierID));
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(verticals + "");
            }else{
                String skierID = urlParts[1];
                StringBuilder sb = new StringBuilder();
                String line;


                String[] body = new String[2];
                int i = 0;
                // Extract values from req.body
                String s;
                while ((line = req.getReader().readLine()) != null) {
                    sb.append(line);
//                    if(i == 1 || i == 2){
//                        body[i-1] = s.split(": ")[1];
//                    }
//                    i++;
                }

                String jsonString = sb.toString();
                JSONObject jsonObject = new JSONObject(jsonString);
                int resort = jsonObject.optInt("resort", -1); // -1 is a default value if "resort" key doesn't exist
                int season = jsonObject.optInt("season", -1);

//                // if resortID is not provided
                if(resort == -1){
                    throw new Exception("resortID is not provided");
                }
//                body[0] = body[0].substring(0, body[1].length() - 1);

                List<SeasonVertical> seasonVerticals = liftRideDao.getTotalVerticalForSkier(Integer.valueOf(skierID), resort, season);
                Gson gson = new Gson();

                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().write(gson.toJson(seasonVerticals));
            }
        }catch (Exception e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad URL path parameters: " + urlPath + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isUrlValid(String[] urlParts, boolean post) {
        // Validate the request url path according to the API spec
        // POST
        // urlPath  = "/1/seasons/2019/days/1/skiers/123"
        // urlParts = [, 1, seasons, 2019, days, 1, skiers, 123]
        // GET
        // urlPath  = "/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"
        // urlPath  = "/{skierID}/vertical"
        if(post || urlParts.length == 8){
            if (urlParts.length == 8 && urlParts[2].equals("seasons") && urlParts[4].equals("days")
                    && urlParts[6].equals("skiers")){
                return true;
            }
        }else{
            if(urlParts.length == 3 && urlParts[2].equals("vertical")){
                return true;
            }
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
        if(!isUrlValid(urlParts, true)){
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