package clients.client1;

import java.util.concurrent.CountDownLatch;

public class Client1ThreadHelper0 {
//    public static final ExecutorService executor = Executors.newFixedThreadPool(32);
    public static void client1StartNThreads(int n, int requestPerThread) {
        CountDownLatch latch = new CountDownLatch(n);
        Client1Runnable0[] runnables = new Client1Runnable0[n];
        Thread[] threads = new Thread[n];
        for(int i = 0; i < n; i++){
            runnables[i] = new Client1Runnable0("thread"+ i, latch, requestPerThread);
//            executor.submit(runnables[i]);
        }

//        Client1Runnable runnable = new Client1Runnable("thread", latch, requestPerThread);
        for(int i = 0; i < n; i++){
//            executor.submit(runnable);
            threads[i] = new Thread(runnables[i]);
        }
//
        for(int i = 0; i < n; i++){
            threads[i].start();
        }

        try{
            latch.await();
            for(int i = 0; i < n; i++){
                threads[i].join();
            }
        }catch (InterruptedException e){

        }
    }
}

