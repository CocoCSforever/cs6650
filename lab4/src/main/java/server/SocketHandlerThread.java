package server;


import java.io.*;
import java.net.Socket;


/*
* Simple thread to handle a socket request
* Author: Ian Gorton
*/
class SocketHandlerThread extends Thread {
  private final Socket clientSocket;
  private boolean running = true;
  private final ActiveCount threadCount;

  SocketHandlerThread(Socket s, ActiveCount threads) {
    clientSocket = s;
    threadCount = threads;
  }

  public void run() {
    threadCount.incrementCount();
    System.out.println("Accepted Client: Address - "
        + clientSocket.getInetAddress().getHostName());
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
      
      String clientID = in.readLine();
      System.out.println("Client ID is :" + clientID);
      out.println("Active Server Thread Count = " + Integer.toString( threadCount.getCount() ));
      out.flush();
//      System.out.println("Reply sent");
//      in.close();
//      out.close();


      // yijia's version
//      String clientID;
//      while( (clientID = in.readLine()) != null){
//        System.out.println(clientID);
//        out.println("Active Server Thread Count = " + Integer.toString( threadCount.getCount() ));
//        System.out.println("Reply sent");
//      }
//      System.out.println("All Replies sent");
    } catch (Exception e) {
           e.printStackTrace();
    }

    try {
      clientSocket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    threadCount.decrementCount();
    System.out.println("Thread exiting");
  }
} //end class
