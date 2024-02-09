package cs6650;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Vector;

public class Collections1 {
    public static void main( String[] args )
    {
        ArrayList<Integer> list = new ArrayList<>();
        Vector<Integer> v = new Vector<>();
        System.out.println(Thread.currentThread() + ":" + Instant.now());
        for(int i = 0; i < 100000; i++){
            list.add(i);
        }
        System.out.println(Thread.currentThread() + ":" + Instant.now());

        System.out.println(Thread.currentThread() + ":" + Instant.now());
        for(int i = 0; i < 100000; i++){
            v.add(i);
        }
        System.out.println(Thread.currentThread() + ":" + Instant.now());
    }
}
//100k
//        Thread[main,5,main]:2024-02-06T08:18:36.435252Z
//        Thread[main,5,main]:2024-02-06T08:18:36.462611Z
//        Thread[main,5,main]:2024-02-06T08:18:36.462873Z
//        Thread[main,5,main]:2024-02-06T08:18:36.471159Z
//        Thread[main,5,main]:2024-02-06T08:24:11.816768Z
//        Thread[main,5,main]:2024-02-06T08:24:11.872259Z
//        Thread[main,5,main]:2024-02-06T08:24:11.872512Z
//        Thread[main,5,main]:2024-02-06T08:24:11.877009Z

// 1000k
//        Thread[main,5,main]:2024-02-06T08:16:47.311741Z
//        Thread[main,5,main]:2024-02-06T08:16:47.362529Z
//        Thread[main,5,main]:2024-02-06T08:16:47.362877Z
//        Thread[main,5,main]:2024-02-06T08:16:47.419052Z

// 10000k
//        Thread[main,5,main]:2024-02-06T08:17:50.956161Z
//        Thread[main,5,main]:2024-02-06T08:17:51.239609Z
//        Thread[main,5,main]:2024-02-06T08:17:51.240406Z
//        Thread[main,5,main]:2024-02-06T08:17:51.480504Z

