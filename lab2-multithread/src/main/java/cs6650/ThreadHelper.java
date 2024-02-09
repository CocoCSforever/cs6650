package cs6650;

import java.util.Map;

public class ThreadHelper {
    public static void startNThreads(int n, Map<Integer, Integer> m){
        NamingThread[] runnables = new NamingThread[n];
        Thread[] threads = new Thread[n];
        for(int i = 0; i < n; i++){
            runnables[i] = new NamingThread("thread"+ i, m);
        }

        for(int i = 0; i < n; i++){
            threads[i] = new Thread(runnables[i]);
        }

        for(int i = 0; i < n; i++){
            threads[i].start();
        }
        try{
            for(int i = 0; i < n; i++){
                threads[i].join();
            }
        }catch (InterruptedException e){
        }
    }

    public static void startNThreads(int n){
        NamingThread[] runnables = new NamingThread[n];
        Thread[] threads = new Thread[n];
        for(int i = 0; i < n; i++){
            runnables[i] = new NamingThread("thread"+ i);
        }

        for(int i = 0; i < n; i++){
            threads[i] = new Thread(runnables[i]);
        }

        for(int i = 0; i < n; i++){
            threads[i].start();
        }
        try{
            for(int i = 0; i < n; i++){
                threads[i].join();
            }
        }catch (InterruptedException e){

        }
    }
}
