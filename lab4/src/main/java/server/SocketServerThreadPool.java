package server;

/**
 *
 * @author Ian Gorton
 * Basic socket server that implements a thread-per-connection model:
 * 1) starts and listens for connections on port 12031
 * 2) When a connection received, spawn a thread to handle connection
 * 
 */



import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketServerThreadPool {
  private static final int THREAD_POOL_SIZE = 30;

  public static void main(String[] args) throws Exception {
    ServerSocket m_ServerSocket;
    ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    try {
      // create socket listener
      m_ServerSocket = new ServerSocket(12031);

      // create object to count active threads
      ActiveCount threadCount = new ActiveCount();
      System.out.println("Server started .....");
      while (true) {
        // accept connection and start thread
        Socket clientSocket = m_ServerSocket.accept();
        threadPool.execute(new SocketHandlerRunnable(clientSocket, threadCount));
      }
    } finally {
      threadPool.shutdown();
    }
  }
}
