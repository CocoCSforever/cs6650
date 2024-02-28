package clients.client1;

public class Client1_Multi_Threads {
    public static void main( String[] args )
    {
        //Display info about the main thread and terminate
//        long startTime = System.currentTimeMillis();
//        System.out.println(Thread.currentThread() + ":" + );
        long wallTime1 = Client1ThreadHelper.client1StartNThreads(32, 1000);
        long wallTime2 =Client1ThreadHelper.client1StartNThreads(32, 5250);
        long time = wallTime1 + wallTime2;
//        long time = System.currentTimeMillis()-startTime;
//        System.out.println(Thread.currentThread() + ":" + System.currentTimeMillis());

        Client1ThreadHelper.executor.shutdown();
        System.out.println("1. number of successful requests sent: " + Client1Runnable.getSuccessCounter());
        System.out.println("2. number of unsuccessful requests (should be 0): " + Client1Runnable.getFailCounter());
        System.out.println("3. total run time (wall time) for all phases to complete: " + time);
        System.out.println("4. total throughput in requests per millisecond : " + 1.0*Client1Runnable.getSuccessCounter()/time);
    }
}

// by running 10000 requests in one thread:
// average run time for 1 request to complete: 1.8/1.9
// 32 = arrival/exit rate * average time per request(2)
// arrival rare = 16