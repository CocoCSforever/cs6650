package client;

/**
 * Simple skeleton socket client thread that coordinates termination
 * with a cyclic barrier to demonstration barrier synchronization
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

// Sockets of this class are coordinated  by a CyclicBarrier which pauses all threads 
// until the last one completes. At this stage, all threads terminate
public class SocketClientThread extends Thread {
    private long clientID;
    private String hostName;
    private int port;
    private final CyclicBarrier barrier;
    private static int MAX_ITERATIONS = 1000;
    private PrintWriter out;
    private BufferedReader in;
    
    public SocketClientThread(String hostName, int port, CyclicBarrier barrier) {
        this.hostName = hostName;
        this.port = port;
//        this.clientID = Thread.currentThread().getId();
        this.barrier = barrier;
    }
    
    public void run() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // TO DO insert code to pass 1k messages to the SocketServer
            Socket s = null;
            try {
                s = new Socket(hostName, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                clientID = Thread.currentThread().getId();
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));

//                System.out.println(i);
                out.println("ClientThread" + clientID + ": ClientMessage" + i);
                System.out.println(in.readLine());
//                System.out.println("get server response");

            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        }
        try {
            in.close();
            out.close();
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}