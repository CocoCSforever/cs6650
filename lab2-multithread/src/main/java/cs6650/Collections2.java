package cs6650;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Collections2 {

    public static void main( String[] args )
    {
        int n = 10000000;
        Hashtable<Integer, Integer> t = new Hashtable();
        HashMap<Integer, Integer> m = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        for(int i = 0; i < n; i++){
            t.put(i, i);
        }
        System.out.println(Thread.currentThread() + ":" + Instant.now());

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        for(int i = 0; i < n; i++){
            m.put(i, i);
        }
        System.out.println(Thread.currentThread() + ":" + Instant.now());

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        for(int i = 0; i < n; i++){
            map.put(i, i);
        }
        System.out.println(Thread.currentThread() + ":" + Instant.now());
    }}

//100k
//    Thread[main,5,main]:2024-02-06T08:27:56.592016Z
//    Thread[main,5,main]:2024-02-06T08:27:56.633459Z
//    Thread[main,5,main]:2024-02-06T08:27:56.634143Z
//    Thread[main,5,main]:2024-02-06T08:27:56.642910Z
//    Thread[main,5,main]:2024-02-06T08:27:56.643269Z
//    Thread[main,5,main]:2024-02-06T08:27:56.701295Z

//1000k
//        Thread[main,5,main]:2024-02-06T08:28:58.864115Z
//        Thread[main,5,main]:2024-02-06T08:28:58.966912Z
//        Thread[main,5,main]:2024-02-06T08:28:58.967553Z
//        Thread[main,5,main]:2024-02-06T08:28:59.045889Z
//        Thread[main,5,main]:2024-02-06T08:28:59.046215Z
//        Thread[main,5,main]:2024-02-06T08:28:59.176906Z


//10000k
//        Thread[main,5,main]:2024-02-06T08:29:54.527695Z
//        Thread[main,5,main]:2024-02-06T08:29:56.000899Z
//        Thread[main,5,main]:2024-02-06T08:29:56.005896Z
//        Thread[main,5,main]:2024-02-06T08:29:57.800905Z
//        Thread[main,5,main]:2024-02-06T08:29:57.817728Z
//        Thread[main,5,main]:2024-02-06T08:30:00.327603Z