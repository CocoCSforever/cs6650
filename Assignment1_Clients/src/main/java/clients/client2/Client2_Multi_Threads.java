package clients.client2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Client2_Multi_Threads {
    public static void main(String[] args)
    {
        //Display info about the main thread and terminate
        String filePath = "April-output.csv";
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(filePath))) {
            Client2Runnable.setBf(bf);
            bf.write("Start Time,Request Type,Latency,Response Code\n");

            long wallTime1 = Client2ThreadHelper.client2StartNThreads(32, 1000, bf);
            long wallTime2 = Client2ThreadHelper.client2StartNThreads(120, 1400, bf);
            long time = wallTime1 + wallTime2;

            Client2ThreadHelper.executor.shutdown();
            Client2ProducerConsumer.executor.shutdown();
            Client2Runnable.executor.shutdown();
            bf.write(""+time);
            System.out.println("1. number of successful requests sent: " + Client2Runnable.getSuccessCounter());
            System.out.println("2. number of unsuccessful requests (should be 0): " + Client2Runnable.getFailCounter());
            System.out.println("3. total run time (wall time) for all phases to complete: " + 1.0*time/1000 + " seconds");
            System.out.println("4. total throughput in requests per second : " + 1.0* Client2Runnable.getSuccessCounter()/time*1000);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}