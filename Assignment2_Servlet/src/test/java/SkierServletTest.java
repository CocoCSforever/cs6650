import com.google.gson.Gson;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import db.LiftRide;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkierServletTest extends TestCase {
    // Number of threads to deploy in each test
    private static final int NUM_THREADS = 200;
    // Number of messages to publish in each thread
    private static final int NUM_ITERATIONS = 100;
    // Number of channels to add to pools
    private static final int NUM_CHANS = 30;
    // For Apache pool example, this allows the pool size to grow to ~= the same number of concurrent threads
    // that utilize the pool. Pass to config.setMaxWait(..) method to allow this behaviour
    private static final int ON_DEMAND = -1;
    // RMQ broker machine
    private static final String SERVER = "localhost";
    // test queue name
    private static final String QUEUE_NAME = "test";
    // the durtaion in seconds a client waits for a channel to be available in the pool
    // Tune value to meet request load and pass to config.setMaxWait(...) method
    private static final int WAIT_TIME_SECS = 1;

    private void showTestConfig() {
        System.out.println("INFO: RabbitMQ Channel Pool Examples");
        System.out.println("INFO: Test Configuration");
        System.out.println("INFO: ==================");
        System.out.println(" ");
        System.out.println("INFO: Number of Threads per Test: " + NUM_THREADS);
        System.out.println("INFO: Number of messages to publish per thread: " + NUM_ITERATIONS);
        System.out.println("INFO: Channel Pool Size: " + NUM_CHANS);
        System.out.println("INFO: Queue name: " + QUEUE_NAME);
        System.out.println("INFO: RMQ Broker: " + SERVER);
        System.out.println(" ");
        System.out.println("INFO: ==============================");
        System.out.println(" ");
    }

    public void testSendMessageToBroker() throws IOException, TimeoutException {
        // create object to run tests
        SkierServletTest test = new SkierServletTest();
        test.showTestConfig();

        System.out.println("INFO: RabbitMQ connection established");
        SkierServlet sk = new SkierServlet();
        Gson gson = new Gson();


        // run ApachePoolTest and calculate the duration it takes
        long start = System.nanoTime();
        for(int t = 0; t < NUM_THREADS; t++){
            for(int i = 0; i < NUM_ITERATIONS; i++){
                SkierServlet.sendMessageToBroker(gson.toJson(new LiftRide("11", "22", "33", "44", "55", "66")));
            }
        }
        long duration = System.nanoTime() - start;
        long convert = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);

        System.out.println("INFO: Test Duration =  " + convert + " milliseconds");
        System.out.println("INFO: Apache Pool Test Complete - hit any key to continue");
    }
}