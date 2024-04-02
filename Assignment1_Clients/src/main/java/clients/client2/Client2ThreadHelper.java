package clients.client2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client2ThreadHelper {
    public static final int SINGLE_THREAD = 1;
    public static final ExecutorService executor = Executors.newFixedThreadPool(SINGLE_THREAD);
    public static long client2StartNThreads(int nOfThreads, int requestPerThread, BufferedWriter bf) {
        // Create a producerConsumer instance
        Client2ProducerConsumer producerConsumer = new Client2ProducerConsumer(nOfThreads, requestPerThread);
        // Use a single dedicated thread to generate all liftRide Information
        generateLiftRideData(producerConsumer, new CountDownLatch(SINGLE_THREAD));
        Client2Runnable.setLatch(new CountDownLatch(nOfThreads));

        long startTime = System.currentTimeMillis();
        sendClientRequest(producerConsumer);

        try {
            Client2Runnable.getLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long wallTime = System.currentTimeMillis()-startTime;
        return wallTime;
    }

    public static void generateLiftRideData(Client2ProducerConsumer producerConsumer, CountDownLatch latch){
        executor.submit(() -> {
            try {
                producerConsumer.produce();
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // wait until producer generated all liftRideInfo
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

    public static void sendClientRequest(Client2ProducerConsumer producerConsumer){
        executor.submit(() -> {
            try {
                producerConsumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

