/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;
import db.LiftRide;
import dbManager.LiftRideDao;

public class RecvMT {

    private final static String QUEUE_NAME = "yjQueue";
    private final static int NUM_THREADS = 80;
    private static final String SERVER = "54.213.139.161";
    private final static ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);
//    private static ConcurrentHashMap<Integer, List<LiftRide>> concurrentMap = new ConcurrentHashMap<>();
    private static LiftRideDao liftRideDao = new LiftRideDao();

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER);
        factory.setUsername("a");
        factory.setPassword("a");
        final Connection connection = factory.newConnection();
        
        Runnable runnable = () -> {
            try {
                final Channel channel = connection.createChannel();
                Gson gson = new Gson();
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                // max one message per receiver
                channel.basicQos(1);
                System.out.println(" [*] Thread waiting for messages. To exit press CTRL+C");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
//                        System.out.println(message);
                    LiftRide liftRide = gson.fromJson(message, LiftRide.class);
                    System.out.println(liftRide);
                    liftRideDao.createLiftRide(liftRide);
//                        addLiftRideToMap(liftRide);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//                        System.out.println( "Callback thread ID = " + Thread.currentThread().getId() + " Received '" + message + "'");
                };
                // process messages
                channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
                } catch (IOException ex) {
                    Logger.getLogger(RecvMT.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

        for(int i = 0; i < NUM_THREADS; i++){
            threadPool.submit(runnable);
        }
    }

//    public static void addLiftRideToMap(LiftRide liftRide){
//        int skierId = liftRide.getSkierID();
//        if(!concurrentMap.containsKey(skierId)){
//            concurrentMap.put(skierId, new ArrayList<>());
//        }
//        concurrentMap.get(skierId).add(liftRide);
//        System.out.println( "Callback thread ID = " + Thread.currentThread().getId() + " Received '" + skierId + "'" + concurrentMap.get(skierId).size());
//    }
}