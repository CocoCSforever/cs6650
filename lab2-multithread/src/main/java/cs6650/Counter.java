package cs6650;

import java.rmi.Naming;
import java.sql.Timestamp;
import java.time.Instant;
import static cs6650.ThreadHelper.startNThreads;

/**
 * Hello world!
 *
 */
public class Counter
{
    public static void main( String[] args )
    {

//        // create 3 threads and give them names
//        NamingThread name0 = new NamingThread("thread0");
//        NamingThread name1 = new NamingThread("thread1");
//        NamingThread name2 = new NamingThread("thread2");
//
//        //Create the threads
//        Thread t0 = new Thread (name0);
//        Thread t1 = new Thread (name1);
//        Thread t2 = new Thread (name2);
//
//        // start the threads
//        t0.start();       t1.start();       t2.start();
//        try {//delay the main thread for a second
//            Thread.currentThread().sleep(1000);
//        } catch (InterruptedException e) {}

        //Display info about the main thread and terminate
        System.out.println(Thread.currentThread() + ":" + Instant.now());
        startNThreads(10000);
        System.out.println(Thread.currentThread() + ":" + Instant.now());
    }
}





