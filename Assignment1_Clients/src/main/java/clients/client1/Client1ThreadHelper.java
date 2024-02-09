package clients.client1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1ThreadHelper {
    public static final ExecutorService executor = Executors.newFixedThreadPool(32);
    public static void client1StartNThreads(int n, int requestPerThread) {

        CountDownLatch latch = new CountDownLatch(n);

        Client1Runnable[] runnables = new Client1Runnable[n];
//        Thread[] threads = new Thread[n];
        for(int i = 0; i < n; i++){
            runnables[i] = new Client1Runnable("thread"+ i, latch, requestPerThread);
            executor.submit(runnables[i]);
        }

//        Client1Runnable runnable = new Client1Runnable("thread", latch, requestPerThread);
//        for(int i = 0; i < n; i++){
//            executor.submit(runnable);
////            threads[i] = new Thread(runnables[i]);
//        }
//
//        for(int i = 0; i < n; i++){
//            threads[i].start();
//        }



        try{
            latch.await();
        }catch (InterruptedException e){

        }
    }
}
