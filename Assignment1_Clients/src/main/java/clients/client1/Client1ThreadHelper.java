package clients.client1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1ThreadHelper {
//    public static final ExecutorService executor = Executors.newFixedThreadPool(32);
    public static void client1StartNThreads(int n, int requestPerThread) {
        Client1ProducerConsumer producerConsumer = new Client1ProducerConsumer(n, requestPerThread);
        CountDownLatch latch = new CountDownLatch(n);

        // Start the producer threads (example with a simple task)
        for (int i = 0; i < 32; i++) {
//            final int threadNumber = i;
            Thread producerThread = new Thread(() -> {
                try {
                    producerConsumer.produce();
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            producerThread.start();
        }

        // Start the consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                producerConsumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumerThread.start();

        try {
            latch.await();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//        CountDownLatch latch = new CountDownLatch(n);
//
//        Client1Runnable[] runnables = new Client1Runnable[n];
//        Thread[] threads = new Thread[n];
//        for(int i = 0; i < n; i++){
//            runnables[i] = new Client1Runnable("thread"+ i, latch, requestPerThread);
////            executor.submit(runnables[i]);
//        }
//
////        Client1Runnable runnable = new Client1Runnable("thread", latch, requestPerThread);
//        for(int i = 0; i < n; i++){
////            executor.submit(runnable);
//            threads[i] = new Thread(runnables[i]);
//        }
////
//        for(int i = 0; i < n; i++){
//            threads[i].start();
//        }
//
//
//
//        try{
//            latch.await();
//        }catch (InterruptedException e){
//
//        }
}

