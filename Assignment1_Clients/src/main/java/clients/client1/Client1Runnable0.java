package clients.client1;

import com.squareup.okhttp.OkHttpClient;
import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Client1Runnable0 implements Runnable {
    private static int successCounter = 0;
    private static int failCounter = 0;
    private static int maxRetries = 5;
//    public static final ExecutorService executor = Executors.newFixedThreadPool(16);
    private String name;
    private CountDownLatch latch;
    private SkiersApi api;
    private int requestPerThread;


    // default constructor
    public Client1Runnable0() {
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = 1;
    }

    public Client1Runnable0(String threadName, CountDownLatch latch, int requestPerThread) {
//        System.out.println("Constructor called: " + threadName) ;
        this.name = threadName ;
        this.latch = latch;
        ApiClient apiClient = new ApiClient();
        OkHttpClient okHttpClient = apiClient.getHttpClient();
        okHttpClient.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = requestPerThread;
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public void run() {
        System.out.println("running: " + name + getSuccessCounter()) ;
        Random r = new Random();
        CountDownLatch currentLatch = new CountDownLatch(1);
        for(int i = 0; i < requestPerThread; i++) {
            api.getApiClient().setBasePath("http://localhost:8080/Assignment1_Servlet_war_exploded/");
//            api.getApiClient().setBasePath("http://184.73.133.33:8080/Assignment1_yijia/");
            LiftRide body = new LiftRide(r.nextInt(360) + 1, r.nextInt(40) + 1);
            Integer resortID = r.nextInt(10) + 1;
            String seasonID = "2024";
            String dayID = "1";
            Integer skierID = r.nextInt(10000) + 1;

            try {
                api.writeNewLiftRideAsync(body, resortID, seasonID, dayID, skierID, new ApiCallback<Void>() {
                    @Override
                    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                        incrementFailCounter();
                    }

                    @Override
                    public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                        currentLatch.countDown();
                        incrementSuccessCounter();
                    }

                    @Override
                    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                    }

                    @Override
                    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                    }
                });
            } catch (ApiException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try{
            currentLatch.await();
            latch.countDown();
        }catch ( Exception e){

        }
    }

    // Synchronize access to the success counter
    public static synchronized void incrementSuccessCounter() {
        successCounter++;
    }

    // Synchronize access to the fail counter
    public static synchronized void incrementFailCounter() {
        failCounter++;
    }

    // Getters for counters (optional)
    public static synchronized int getSuccessCounter() {
        return successCounter;
    }

    public static synchronized int getFailCounter() {
        return failCounter;
    }

}