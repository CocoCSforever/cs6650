package clients.client1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1ThreadHelper {
    public static final ExecutorService executor = Executors.newFixedThreadPool(32);
    public static long client1StartNThreads(int n, int requestPerThread) {
        Client1ProducerConsumer producerConsumer = new Client1ProducerConsumer(n, requestPerThread);

        generateLiftRideData(producerConsumer, new CountDownLatch(n));
        Client1Runnable.setLatch(new CountDownLatch(n));
        long startTime = System.currentTimeMillis();
        sendClientRequest(producerConsumer);

//        executor.shutdown();
        try {
            Client1Runnable.getLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long wallTime = System.currentTimeMillis()-startTime;
        return wallTime;
    }

    public static void generateLiftRideData(Client1ProducerConsumer producerConsumer, CountDownLatch latch){
        // Start the producer threads (example with a simple task)
        for (int i = 0; i < 32; i++) {
            executor.submit(() -> {
                try {
                    producerConsumer.produce();
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // wait until producer generated all liftRideInfo
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendClientRequest(Client1ProducerConsumer producerConsumer){
        for (int i = 0; i < 32; i++) {
            executor.submit(() -> {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

