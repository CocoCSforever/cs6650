package clients.client1;

import com.google.common.util.concurrent.RateLimiter;
import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRideInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Client1Runnable implements Runnable {
    private static int successCounter = 0;
    private static int failCounter = 0;
    private static final int maxRetries = 5;
    private static CountDownLatch latch;
    private static RateLimiter rateLimiter = RateLimiter.create(800);
//    public static final ExecutorService executor = Executors.newFixedThreadPool(16);
    private String name;
    private SkiersApi api;
    private int requestPerThread;
    private ArrayList<LiftRideInfo> requests;
//    private String basePath = "http://lb-1718237016.us-west-2.elb.amazonaws.com/Assignment2_Servlet/";
    private String basePath = "http://54.214.86.230:8080/Assignment3_Servlet_war/";
//    private String basePath = "http://localhost:8080/Assignment2_Servlet_war/";
//    "http://lb-699851470.us-west-2.elb.amazonaws.com/Assignment2_yijia/"


    // default constructor
    public Client1Runnable(int requestPerThread) {
        this.api = new SkiersApi(new ApiClient());
        this.requestPerThread = requestPerThread;
        this.requests = new ArrayList<>();
    }

    @Override
    public void run() {
//        System.out.println("running client with Success: " + getSuccessCounter());
//        api.getApiClient().setBasePath("http://localhost:8080/Assignment2_Servlet_war/");
        api.getApiClient().setBasePath(basePath);
        api.getApiClient().setConnectTimeout(500000);
        api.getApiClient().setReadTimeout(500000);
        for(LiftRideInfo request: requests){
            makeAsyncApiCall(request);
        }
//        System.out.println("exiting client with total Success count: " + getSuccessCounter());
        latch.countDown();
    }

    public void makeAsyncApiCall(LiftRideInfo request) {
//        LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID
        int retryCount = 0;
        while (retryCount < maxRetries) {
            rateLimiter.acquire();
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
//                        System.out.println("API call successful. Status code: " + statusCode);
                        future.complete(new ApiResponse<Void>(statusCode, responseHeaders, result));
                        incrementSuccessCounter();
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
                    break;
                }else{
                    retryCount++;
                }
            } catch (ApiException | ExecutionException | InterruptedException e) {
                retryCount++;
            }
        }
    }

    public synchronized void addToRequests(LiftRideInfo liftRideInfo){
        requests.add(liftRideInfo);
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public static CountDownLatch getLatch() {
        return latch;
    }

    public static void setLatch(CountDownLatch latch) {
        Client1Runnable.latch = latch;
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