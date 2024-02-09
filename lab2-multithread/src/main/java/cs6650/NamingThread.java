package cs6650;

import java.util.Map;

class NamingThread implements Runnable {
    private static int counter = 0;
    private String name;
    private Map<Integer, Integer> m;

    // default constructor
    public NamingThread() {}

    public NamingThread(String threadName) {
        System.out.println("Constructor called: " + threadName) ;
        name = threadName ;
    }

    public NamingThread(String threadName, Map<Integer, Integer> input) {
//        System.out.println("Constructor called: " + threadName) ;
        m = input;
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public void run() {
        //Display info about this  thread
//        System.out.println("Run called : " + name);
//        increCounter();
//        System.out.println(name + " : " + Thread.currentThread());
//        System.out.println(counter);
        // and terminate silently ....
        addToMap();
    }

    public void increCounter(){
        for(int i = 0; i < 10; i++) {
//            this.counter++;
            counter += 1;
        }
    }

    public void addToMap(){
        for(int i = 0; i < 10; i++) {
            counter += 1;
            m.put(counter, counter);
        }
    }
}