package clients.client1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1ThreadHelper {
    public static final int SINGLE_THREAD = 1;
    public static final ExecutorService executor = Executors.newFixedThreadPool(SINGLE_THREAD);
    public static long client1StartNThreads(int nOfThreads, int requestPerThread) {
        // Create a producerConsumer instance
        Client1ProducerConsumer producerConsumer = new Client1ProducerConsumer(nOfThreads, requestPerThread);
        // Use a single dedicated thread to generate all liftRide Information
        generateLiftRideData(producerConsumer, new CountDownLatch(SINGLE_THREAD));
        Client1Runnable.setLatch(new CountDownLatch(nOfThreads));

        // start to send client requests
        long startTime = System.currentTimeMillis();
        sendClientRequest(nOfThreads, producerConsumer);

        try {
            Client1Runnable.getLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long wallTime = System.currentTimeMillis()-startTime;
        return wallTime;
    }

    public static void generateLiftRideData(Client1ProducerConsumer producerConsumer, CountDownLatch latch){
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
    

    public static void sendClientRequest(int nOfThreads, Client1ProducerConsumer producerConsumer){
//        for (int i = 0; i < nOfThreads; i++) {
            executor.submit(() -> {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
//        }
    }
}

