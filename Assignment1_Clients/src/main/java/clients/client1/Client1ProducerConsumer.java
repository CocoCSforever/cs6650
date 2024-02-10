package clients.client1;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Client1ProducerConsumer {
    private static final ExecutorService executor = Executors.newFixedThreadPool(32);
    private static final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private static int capacity;
    private static int requestsPerThread;


    public Client1ProducerConsumer(int capacity, int requestsPerThread) {
        this.capacity = capacity;
        this.requestsPerThread = requestsPerThread;
    }

    public void produce() throws InterruptedException
    {
        int producedCount = 0;
        while (producedCount < requestsPerThread) {
            synchronized (this)
            {
                // producer thread waits while list
                // is full
                while (taskQueue.size() == capacity)
                    wait();

                System.out.println("Producer produced-");

                // to insert the jobs in the list
                taskQueue.add(new Client1Runnable());

                // notifies the consumer thread that
                // now it can start consuming
                notify();

                // makes the working of program easier
                // to  understand
//                Thread.sleep(1000);
                producedCount++;
            }
        }
    }

    public void consume() throws InterruptedException
    {
        int consumedCount = 0;
        while (consumedCount < requestsPerThread) {
            synchronized (this)
            {
                // consumer thread waits while list
                // is empty
                while (taskQueue.size() == 0)
                    wait();

                // to retrieve the first job in the list
                Runnable val = taskQueue.poll();
                executor.submit(val);
                consumedCount++;

                System.out.println("Consumer consumed-");

                // Wake up producer thread
                notify();

                // and sleep
//                Thread.sleep(1000);
            }
        }
    }
}
