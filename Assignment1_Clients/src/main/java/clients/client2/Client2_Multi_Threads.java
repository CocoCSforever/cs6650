package clients.client2;

import static clients.client2.Client2ThreadHelper.client2StartNThreads;

public class Client2_Multi_Threads {
    public static void main( String[] args )
    {
        //Display info about the main thread and terminate
        long startTime = System.currentTimeMillis();
//        System.out.println(Thread.currentThread() + ":" + );
        client2StartNThreads(32, 1000, "output.csv");
        client2StartNThreads(32, 5250, "output.csv");
        long time = System.currentTimeMillis()-startTime;
//        System.out.println(Thread.currentThread() + ":" + System.currentTimeMillis());

        System.out.println("1. number of successful requests sent: " + Client2Runnable.getSuccessCounter());
        System.out.println("2. number of unsuccessful requests (should be 0): " + Client2Runnable.getFailCounter());
        System.out.println("3. total run time (wall time) for all phases to complete: " + time);
        System.out.println("4. total throughput in requests per second : " + Client2Runnable.getSuccessCounter()/time);
    }
}