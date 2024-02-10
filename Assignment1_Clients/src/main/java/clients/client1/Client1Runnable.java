package clients.client1;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import com.squareup.okhttp.OkHttpClient;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Client1Runnable implements Runnable {
    private static int successCounter = 0;
    private static int failCounter = 0;
    private static final int maxRetries = 5;
    public static final ExecutorService executor = Executors.newFixedThreadPool(16);
    private String name;
    private CountDownLatch latch;
    private SkiersApi api;
    private int requestPerThread;


    // default constructor
    public Client1Runnable() {
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = 1;
    }

    public Client1Runnable(String threadName, CountDownLatch latch, int requestPerThread) {
//        System.out.println("Constructor called: " + threadName) ;
        this.name = threadName ;
        this.latch = latch;
        ApiClient apiClient = new ApiClient();
        OkHttpClient okHttpClient = apiClient.getHttpClient();
        okHttpClient.setConnectTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
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
//        for(int i = 0; i < requestPerThread; i++) {
            api.getApiClient().setBasePath("http://localhost:8080/Assignment1_Servlet_war_exploded/");
//            api.getApiClient().setBasePath("http://184.73.133.33:8080/Assignment1_yijia/");
            LiftRide body = new LiftRide(r.nextInt(360) + 1, r.nextInt(40) + 1);
            Integer resortID = r.nextInt(10) + 1;
            String seasonID = "2024";
            String dayID = "1";
            Integer skierID = r.nextInt(10000) + 1;

            makeAsyncApiCall(0, body, resortID, seasonID, dayID, skierID, new ApiCallback<Void>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    // Handle failure
//                    System.out.println("API call failed.Status code: " + statusCode);
                    e.printStackTrace();
                    incrementFailCounter();
                }

                @Override
                public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                    // Handle success
//                    System.out.println("API call successful. Status code: " + statusCode);
                    incrementSuccessCounter();
//                    currentLatch.countDown();
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    // Handle upload progress
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    // Handle download progress
                }
            });
//        }

        try{
            currentLatch.await();
//            latch.countDown();
        }catch (InterruptedException e){

        }
    }

    public void makeAsyncApiCall(int retryCount, LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID, final ApiCallback<Void> callback) {
        if (retryCount >= maxRetries) {
            // Handle max retries reached
            return;
        }

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                api.writeNewLiftRideAsync(body, resortID, seasonID, dayID, skierID, callback);
            } catch (ApiException | ExecutionException | InterruptedException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
//                e.printStackTrace();
            }
        }, executor);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                makeAsyncApiCall(retryCount + 1, body, resortID, seasonID, dayID, skierID, callback);
            } else {

            }
        });
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