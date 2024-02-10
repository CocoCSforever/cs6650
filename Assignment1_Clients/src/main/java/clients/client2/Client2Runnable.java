package clients.client2;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Client2Runnable implements Runnable {
    private static int successCounter = 0;
    private static int failCounter = 0;
    private static BufferedWriter bf;
    private String name;
    private CountDownLatch latch;
    private SkiersApi api;
    private int requestPerThread;

    // default constructor
    public Client2Runnable() {}

    public Client2Runnable(String threadName, CountDownLatch latch, int requestPerThread, BufferedWriter bf) {
//        System.out.println("Constructor called: " + threadName) ;
        this.name = threadName ;
        this.latch = latch;
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = requestPerThread;
        this.bf = bf;
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public void run() {
        Random r = new Random();
        for (int i = 0; i < requestPerThread; i++) {
            api.getApiClient().setBasePath("http://localhost:8080/Assignment1_Servlet_war_exploded/");
            LiftRide body = new LiftRide(r.nextInt(360) + 1, r.nextInt(40) + 1);
            Integer resortID = r.nextInt(10) + 1;
            String seasonID = "2024";
            String dayID = "1";
            Integer skierID = r.nextInt(10000) + 1;

            int maxRetries = 5;
            int retryCount = 0;

            while (retryCount < maxRetries) {
                try {
                    long startTime = System.currentTimeMillis();
                    CompletableFuture<Void> future = new CompletableFuture<>();
                    api.writeNewLiftRideAsync(body, resortID, seasonID, dayID, skierID, new ApiCallback<Void>() {
                        @Override
                        public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                            // Handle failure
                            System.out.println("API call failed. Status code: " + statusCode);
//                            e.printStackTrace();
                            future.completeExceptionally(e);
                            incrementFailCounter();
                        }

                        @Override
                        public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                            //                        System.out.println("API call successful. Status code: " + statusCode);
                            future.complete(null);
                            incrementSuccessCounter();
                            writeToFile(startTime, "POST", System.currentTimeMillis()-startTime, statusCode);
                        }

                        @Override
                        public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                        }

                        @Override
                        public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                        }
                    });
                    future.get();
                    break;
                } catch (ApiException | ExecutionException | InterruptedException e) {
                    retryCount++;
                    throw new RuntimeException(e);
                }
            }
        }
        latch.countDown();
    }

    public synchronized void writeToFile(long startTime, String requestType, long latency, int responseCode) {
        try {
            bf.write(String.format("%d, %s, %d, %d\n", startTime, requestType, latency, responseCode));
        } catch (IOException e) {
            e.printStackTrace();
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