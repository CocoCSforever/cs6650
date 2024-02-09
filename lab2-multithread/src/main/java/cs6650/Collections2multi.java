package cs6650;

import java.time.Instant;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cs6650.ThreadHelper.startNThreads;

public class Collections2multi {

    public static void main( String[] args )
    {
        int n = 100000;
        Hashtable<Integer, Integer> t = new Hashtable();
        HashMap<Integer, Integer> m = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        startNThreads(100, t);
        System.out.println(Thread.currentThread() + ":" + Instant.now());

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        startNThreads(100, m);
        System.out.println(Thread.currentThread() + ":" + Instant.now());

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        startNThreads(100, map);
        System.out.println(Thread.currentThread() + ":" + Instant.now());
    }
}

//100k
//        Thread[main,5,main]:2024-02-06T08:47:13.529244Z
//        Thread[main,5,main]:2024-02-06T08:47:13.565455Z
//        Thread[main,5,main]:2024-02-06T08:47:13.566194Z
//        Thread[main,5,main]:2024-02-06T08:47:13.572478Z
//        Thread[main,5,main]:2024-02-06T08:47:13.572773Z
//        Thread[main,5,main]:2024-02-06T08:47:13.577630Z

//1000k
//        Thread[main,5,main]:2024-02-06T08:46:37.223830Z
//        Thread[main,5,main]:2024-02-06T08:46:37.266677Z
//        Thread[main,5,main]:2024-02-06T08:46:37.267543Z
//        Thread[main,5,main]:2024-02-06T08:46:37.276817Z
//        Thread[main,5,main]:2024-02-06T08:46:37.277315Z
//        Thread[main,5,main]:2024-02-06T08:46:37.284603Z



//10000k
//        Thread[main,5,main]:2024-02-06T08:45:54.017788Z
//        Thread[main,5,main]:2024-02-06T08:45:54.066897Z
//        Thread[main,5,main]:2024-02-06T08:45:54.067680Z
//        Thread[main,5,main]:2024-02-06T08:45:54.073974Z
//        Thread[main,5,main]:2024-02-06T08:45:54.074316Z
//        Thread[main,5,main]:2024-02-06T08:45:54.085198Z