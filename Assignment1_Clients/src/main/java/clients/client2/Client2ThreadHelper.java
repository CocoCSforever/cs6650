package clients.client2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client2ThreadHelper {
    static ExecutorService executorService = Executors.newFixedThreadPool(50);

    public static void client2StartNThreads(int n, int requestPerThread, String filePath) {
        CountDownLatch latch = new CountDownLatch(n);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write("Start Time,Request Type,Latency,Response Code\n");
            for(int i = 0; i < n; i++){
                executorService.submit(new Client2Runnable("thread"+ i, latch, requestPerThread, writer));
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
