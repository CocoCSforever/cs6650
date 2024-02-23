package client;

/**
 * Skeleton socket client. 
 * Accepts host/port on command line or defaults to localhost/12031
 * Then (should) starts MAX_Threads and waits for them all to terminate before terminating main()
 * @author Ian Gorton
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreaded {
    
    static CyclicBarrier barrier; 
    
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        String hostName;
        final int MAX_THREADS = 50 ;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in SocketServer
        }
        barrier = new CyclicBarrier(MAX_THREADS+1);

        System.out.println(System.currentTimeMillis());
        // TO DO create and start MAX_THREADS SocketClientThread
        for(int i = 0; i < MAX_THREADS; i++){
            Thread thread = new SocketClientThread(hostName, port, barrier);
            thread.start();
        }
        // TO DO wait for all threads to compile
        barrier.await();
        System.out.println(System.currentTimeMillis());
        
        System.out.println("Terminating ....");
    }
}
