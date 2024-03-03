package clients.client1;

public class Client1_Multi_Threads {
    public static void main( String[] args )
    {
        //Display info about the main thread and terminate
        long wallTime1 = Client1ThreadHelper.client1StartNThreads(32, 1000);
        long wallTime2 =Client1ThreadHelper.client1StartNThreads(80, 2100);
        long time = wallTime1 + wallTime2;

        Client1ThreadHelper.executor.shutdown();
        Client1ProducerConsumer.executor.shutdown();
        System.out.println("1. number of successful requests sent: " + Client1Runnable.getSuccessCounter());
        System.out.println("2. number of unsuccessful requests (should be 0): " + Client1Runnable.getFailCounter());
        System.out.println("3. total run time (wall time) for all phases to complete: " + time/1000 + " seconds");
        System.out.println("4. total throughput in requests per second : " + 1.0*Client1Runnable.getSuccessCounter()/time*1000);
    }
}