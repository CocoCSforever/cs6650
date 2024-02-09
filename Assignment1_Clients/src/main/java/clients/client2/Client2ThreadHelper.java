package clients.client2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Client2ThreadHelper {
    public static void client2StartNThreads(int n, int requestPerThread, String filePath) {
        CountDownLatch latch = new CountDownLatch(n);
        Client2Runnable[] runnables = new Client2Runnable[n];
        Thread[] threads = new Thread[n];
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write("Start Time,Request Type,Latency,Response Code\n");
            for(int i = 0; i < n; i++){
                runnables[i] = new Client2Runnable("thread"+ i, latch, requestPerThread, writer);
            }

            for(int i = 0; i < n; i++){
                threads[i] = new Thread(runnables[i]);
            }

            for(int i = 0; i < n; i++){
                threads[i].start();
            }

            try{
                latch.await();
            }catch (InterruptedException e){

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
