package clients.client2;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRideInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Client2Runnable implements Runnable {
    public static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private static int successCounter = 0;
    private static int failCounter = 0;
    private static final int maxRetries = 5;
    private static BufferedWriter bf;
    private static CountDownLatch latch;
    private String name;
    private SkiersApi api;
    private int requestPerThread;
    private ArrayList<LiftRideInfo> requests;

    public Client2Runnable(int requestPerThread) {
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = requestPerThread;
        this.requests = new ArrayList<>();
    }

    @Override
    public void run() {
//        System.out.println("running client with Success: " + getSuccessCounter());
        api.getApiClient().setBasePath("http://localhost:8080/Assignment1_Servlet_war/");
//        api.getApiClient().setBasePath("http://54.82.80.17:8080/Assignment1_yijia/");
        for(LiftRideInfo request: requests){
            makeAsyncApiCall(request);
        }
//        System.out.println("exiting client with total Success count: " + getSuccessCounter());
        latch.countDown();
    }

    public void makeAsyncApiCall(LiftRideInfo request) {
//        LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID
        int retryCount = 0;
        long startTime = System.currentTimeMillis();
        while (retryCount < maxRetries) {
            try {
                CompletableFuture<ApiResponse<Void>> future = new CompletableFuture<>();
                api.writeNewLiftRideAsync(request.getBody(), request.getResortID(), request.getSeasonID(), request.getDayID(), request.getSkierID(), new ApiCallback<Void>() {
                    @Override
                    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                        // Handle failure
//                            System.out.println("API call failed. Status code: " + statusCode);
                        e.printStackTrace();
                        future.completeExceptionally(e);
                        incrementFailCounter();
                    }

                    @Override
                    public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                        if(statusCode >= 400){
                            onFailure(new ApiException("Server responded with error code"), statusCode, responseHeaders);
                            return;
                        }
                        long wallTime = System.currentTimeMillis()-startTime;
//                        System.out.println("API call successful. Status code: " + statusCode);
                        future.complete(new ApiResponse<Void>(statusCode, responseHeaders, result));
                        incrementSuccessCounter();
                        writeToFile(startTime, "POST", wallTime, statusCode);
                    }

                    @Override
                    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

                    }

                    @Override
                    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

                    }
                });
                // This will block until the CompletableFuture is completed
                ApiResponse<Void> response = future.get();
                // Check if the response indicates success; if yes, break the loop
                if (response.getStatusCode() == 201) {
//                    long wallTime = System.currentTimeMillis()-startTime;
//                    writeToFile(startTime, "POST", wallTime, response.getStatusCode());
                    break;
                }else{
                    retryCount++;
                }
            } catch (ApiException | ExecutionException | InterruptedException e) {
                retryCount++;
            }
        }

    }

    public synchronized void writeToFile(long startTime, String requestType, long latency, int responseCode) {
        executor.submit(() -> {
            try {
                bf.write(String.format("%d, %s, %d, %d\n", startTime, requestType, latency, responseCode));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void addToRequests(LiftRideInfo liftRideInfo){
        requests.add(liftRideInfo);
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public static BufferedWriter getBf() {
        return bf;
    }

    public static void setBf(BufferedWriter bf) {
        Client2Runnable.bf = bf;
    }

    public static CountDownLatch getLatch() {
        return latch;
    }

    public static void setLatch(CountDownLatch latch) {
        Client2Runnable.latch = latch;
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