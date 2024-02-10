package clients.client1;

public class Client1_Multi_Threads {
    public static void main( String[] args )
    {
        //Display info about the main thread and terminate
        long startTime = System.currentTimeMillis();
//        System.out.println(Thread.currentThread() + ":" + );
        Client1ThreadHelper.client1StartNThreads(32, 1000);
        Client1ThreadHelper.client1StartNThreads(32, 5250);
        long time = System.currentTimeMillis()-startTime;
//        System.out.println(Thread.currentThread() + ":" + System.currentTimeMillis());

//        Client1ThreadHelper.executor.shutdown();
//        Client1Runnable.executor.shutdown();
        System.out.println("1. number of successful requests sent: " + Client1Runnable.getSuccessCounter());
        System.out.println("2. number of unsuccessful requests (should be 0): " + Client1Runnable.getFailCounter());
        System.out.println("3. total run time (wall time) for all phases to complete: " + time);
        System.out.println("4. total throughput in requests per second : " + 1.0*Client1Runnable.getSuccessCounter()/time);
    }
}


//32 Threads * 1000 request per thread
//Thread[main,5,main]:1707448862430
//Thread[main,5,main]:1707448870766

//Thread[main,5,main]:1707451139635
//Thread[main,5,main]:1707451142684

// runs locally（132k）
//1. number of successful requests sent: 131882
//2. number of unsuccessful requests (should be 0): 0
//3. total run time (wall time) for all phases to complete: 33019
//4. total throughput in requests per second : 3


// runs locally (200k)
// 1. number of successful requests sent: 200000
// 2. number of unsuccessful requests (should be 0): 0
// 3. total run time (wall time) for all phases to complete: 44163
// 4. total throughput in requests per second : 4

// by running 10000 requests in one thread:
// average run time for 1 request to complete: 1.8/1.9
// 32 = arrival/exit rate * average time per request(2)
// arrival rare = 16