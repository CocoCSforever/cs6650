import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class One_HttpClient_Multi {
    // Create an instance of HttpClient.
//    private static HttpClient client = new HttpClient();
    private static String url = "http://localhost:8080/lab3_war_exploded/hello/";
    private final static int NUMTHREADS = 10;
    private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    private static HttpClient client = new HttpClient(connectionManager);


    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis());

        CountDownLatch completed = new CountDownLatch(NUMTHREADS);
        for (int i = 0; i < NUMTHREADS; i++) {
            final int currentIndex = i;
            // lambda runnable creation - interface only has a single method so lambda works fine
            Runnable thread =  () -> { sendRequest(url+currentIndex); completed.countDown();
            };
            new Thread(thread).start();
        }

        completed.await();

        System.out.println(System.currentTimeMillis());
    }

    public static void sendRequest(String url){
        System.out.println(url);
        // Create a method instance.
        GetMethod method = new GetMethod(url);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            System.out.println(new String(responseBody));

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }
}

// 1
// 1707352462232
// http://localhost:8080/lab3_war_exploded/hello/good
// good
// 1707352463295

// 10
//1707352686228
//1707352691401