package clients.client2;

import io.swagger.client.model.LiftRideInfo;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Client2ProducerConsumer {
    public static final ExecutorService executor = Executors.newFixedThreadPool(120);
    private static final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private static int nOfThreads;
    private static int requestsPerThread;


    public Client2ProducerConsumer(int nOfThreads, int requestsPerThread) {
        this.nOfThreads = nOfThreads;
        this.requestsPerThread = requestsPerThread;
    }

    /*
     produce() works with a single dedicated thread to generate $requestsPerThreads ready-to-use liftRide Information for
     each client. It generates nOfThreads clients and add them to taskQueue.
     */
    public void produce() throws InterruptedException
    {
        for (int i = 0; i < nOfThreads; i++) {
            Client2Runnable client = new Client2Runnable(requestsPerThread);
            Random r = new Random();
            for (int j = 0; j < requestsPerThread; j++) {
                client.addToRequests(new LiftRideInfo(r));
            }

            synchronized (this) {
                // producer thread waits while list
                // is full
                while (taskQueue.size() == nOfThreads)
                    wait();

//                System.out.println("Producer produced-" + taskQueue.size());

                // to insert the jobs in the list
                taskQueue.add(client);

                // notifies the consumer thread that
                // now it can start consuming
                notify();
            }
        }
    }

    public void consume() throws InterruptedException
    {
        for (int i = 0; i < nOfThreads; i++) {
            synchronized (this) {
                // consumer thread waits while list
                // is empty
                while (taskQueue.size() == 0)
                    wait();

                notify();

                // to retrieve the first job in the list
                Runnable client = taskQueue.poll();
                executor.submit(client);

//                System.out.println("Consumer consumed-" + taskQueue.size());

                // Wake up producer thread
                notify();
            }
        }
    }
}
