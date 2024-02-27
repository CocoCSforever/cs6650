package clients.client1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1ThreadHelper {
    public static final ExecutorService executor = Executors.newFixedThreadPool(32);
    public static void client1StartNThreads(int n, int requestPerThread) {
        CountDownLatch latch = new CountDownLatch(n);
        Client1Runnable.latch = new CountDownLatch(n);
        Client1ProducerConsumer producerConsumer = new Client1ProducerConsumer(n, requestPerThread, latch);


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

        for (int i = 0; i < 32; i++) {
            executor.submit(() -> {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();

        try {
            Client1Runnable.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

