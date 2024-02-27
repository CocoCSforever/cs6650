/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 1.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.LiftRide;
import io.swagger.client.model.Response;
import io.swagger.client.model.ResponseMsg;
import io.swagger.client.model.SkierVertical;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SkiersApi {
    private ApiClient apiClient;

    public SkiersApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SkiersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getSkierDayVertical
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getSkierDayVerticalCall(Integer resortID, String seasonID, String dayID, Integer skierID, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"
            .replaceAll("\\{" + "resortID" + "\\}", apiClient.escapeString(resortID.toString()))
            .replaceAll("\\{" + "seasonID" + "\\}", apiClient.escapeString(seasonID.toString()))
            .replaceAll("\\{" + "dayID" + "\\}", apiClient.escapeString(dayID.toString()))
            .replaceAll("\\{" + "skierID" + "\\}", apiClient.escapeString(skierID.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getSkierDayVerticalValidateBeforeCall(Integer resortID, String seasonID, String dayID, Integer skierID, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'resortID' is set
        if (resortID == null) {
            throw new ApiException("Missing the required parameter 'resortID' when calling getSkierDayVertical(Async)");
        }
        // verify the required parameter 'seasonID' is set
        if (seasonID == null) {
            throw new ApiException("Missing the required parameter 'seasonID' when calling getSkierDayVertical(Async)");
        }
        // verify the required parameter 'dayID' is set
        if (dayID == null) {
            throw new ApiException("Missing the required parameter 'dayID' when calling getSkierDayVertical(Async)");
        }
        // verify the required parameter 'skierID' is set
        if (skierID == null) {
            throw new ApiException("Missing the required parameter 'skierID' when calling getSkierDayVertical(Async)");
        }
        
        com.squareup.okhttp.Call call = getSkierDayVerticalCall(resortID, seasonID, dayID, skierID, progressListener, progressRequestListener);
        return call;
    }

    /**
     * write a new lift ride for the skier
     * get the total vertical for the skier for the specified ski day
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @return Integer
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Integer getSkierDayVertical(Integer resortID, String seasonID, String dayID, Integer skierID) throws ApiException {
        ApiResponse<Integer> resp = getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
        return resp.getData();
    }

    /**
     * write a new lift ride for the skier
     * get the total vertical for the skier for the specified ski day
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @return ApiResponse&lt;Integer&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Integer> getSkierDayVerticalWithHttpInfo(Integer resortID, String seasonID, String dayID, Integer skierID) throws ApiException {
        com.squareup.okhttp.Call call = getSkierDayVerticalValidateBeforeCall(resortID, seasonID, dayID, skierID, null, null);
        Type localVarReturnType = new TypeToken<Integer>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * write a new lift ride for the skier (asynchronously)
     * get the total vertical for the skier for the specified ski day
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getSkierDayVerticalAsync(Integer resortID, String seasonID, String dayID, Integer skierID, final ApiCallback<Integer> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getSkierDayVerticalValidateBeforeCall(resortID, seasonID, dayID, skierID, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Integer>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getSkierResortTotals
     * @param skierID ID the skier to retrieve data for (required)
     * @param resort resort to filter by (required)
     * @param season season to filter by, optional (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getSkierResortTotalsCall(Integer skierID, List<String> resort, List<String> season, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/skiers/{skierID}/vertical"
            .replaceAll("\\{" + "skierID" + "\\}", apiClient.escapeString(skierID.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (resort != null)
        localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "resort", resort));
        if (season != null)
        localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "season", season));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getSkierResortTotalsValidateBeforeCall(Integer skierID, List<String> resort, List<String> season, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'skierID' is set
        if (skierID == null) {
            throw new ApiException("Missing the required parameter 'skierID' when calling getSkierResortTotals(Async)");
        }
        // verify the required parameter 'resort' is set
        if (resort == null) {
            throw new ApiException("Missing the required parameter 'resort' when calling getSkierResortTotals(Async)");
        }
        
        com.squareup.okhttp.Call call = getSkierResortTotalsCall(skierID, resort, season, progressListener, progressRequestListener);
        return call;
    }

    /**
     * get the total vertical for the skier for specified seasons at the specified resort
     * get the total vertical for the skier the specified resort. If no season is specified, return all seasons
     * @param skierID ID the skier to retrieve data for (required)
     * @param resort resort to filter by (required)
     * @param season season to filter by, optional (optional)
     * @return SkierVertical
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public SkierVertical getSkierResortTotals(Integer skierID, List<String> resort, List<String> season) throws ApiException {
        ApiResponse<SkierVertical> resp = getSkierResortTotalsWithHttpInfo(skierID, resort, season);
        return resp.getData();
    }

    /**
     * get the total vertical for the skier for specified seasons at the specified resort
     * get the total vertical for the skier the specified resort. If no season is specified, return all seasons
     * @param skierID ID the skier to retrieve data for (required)
     * @param resort resort to filter by (required)
     * @param season season to filter by, optional (optional)
     * @return ApiResponse&lt;SkierVertical&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<SkierVertical> getSkierResortTotalsWithHttpInfo(Integer skierID, List<String> resort, List<String> season) throws ApiException {
        com.squareup.okhttp.Call call = getSkierResortTotalsValidateBeforeCall(skierID, resort, season, null, null);
        Type localVarReturnType = new TypeToken<SkierVertical>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * get the total vertical for the skier for specified seasons at the specified resort (asynchronously)
     * get the total vertical for the skier the specified resort. If no season is specified, return all seasons
     * @param skierID ID the skier to retrieve data for (required)
     * @param resort resort to filter by (required)
     * @param season season to filter by, optional (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getSkierResortTotalsAsync(Integer skierID, List<String> resort, List<String> season, final ApiCallback<SkierVertical> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getSkierResortTotalsValidateBeforeCall(skierID, resort, season, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<SkierVertical>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for writeNewLiftRide
     * @param body Specify new Season value (required)
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call writeNewLiftRideCall(LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        
        // create path and map variables
        String localVarPath = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"
            .replaceAll("\\{" + "resortID" + "\\}", apiClient.escapeString(resortID.toString()))
            .replaceAll("\\{" + "seasonID" + "\\}", apiClient.escapeString(seasonID.toString()))
            .replaceAll("\\{" + "dayID" + "\\}", apiClient.escapeString(dayID.toString()))
            .replaceAll("\\{" + "skierID" + "\\}", apiClient.escapeString(skierID.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call writeNewLiftRideValidateBeforeCall(LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling writeNewLiftRide(Async)");
        }
        // verify the required parameter 'resortID' is set
        if (resortID == null) {
            throw new ApiException("Missing the required parameter 'resortID' when calling writeNewLiftRide(Async)");
        }
        // verify the required parameter 'seasonID' is set
        if (seasonID == null) {
            throw new ApiException("Missing the required parameter 'seasonID' when calling writeNewLiftRide(Async)");
        }
        // verify the required parameter 'dayID' is set
        if (dayID == null) {
            throw new ApiException("Missing the required parameter 'dayID' when calling writeNewLiftRide(Async)");
        }
        // verify the required parameter 'skierID' is set
        if (skierID == null) {
            throw new ApiException("Missing the required parameter 'skierID' when calling writeNewLiftRide(Async)");
        }
        
        com.squareup.okhttp.Call call = writeNewLiftRideCall(body, resortID, seasonID, dayID, skierID, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * write a new lift ride for the skier
     * Stores new lift ride details in the data store
     * @param body Specify new Season value (required)
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void writeNewLiftRide(LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID) throws ApiException {
        writeNewLiftRideWithHttpInfo(body, resortID, seasonID, dayID, skierID);
    }

    /**
     * write a new lift ride for the skier
     * Stores new lift ride details in the data store
     * @param body Specify new Season value (required)
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> writeNewLiftRideWithHttpInfo(LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID) throws ApiException {
        com.squareup.okhttp.Call call = writeNewLiftRideValidateBeforeCall(body, resortID, seasonID, dayID, skierID, null, null);
        return apiClient.execute(call);
    }

    /**
     * write a new lift ride for the skier (asynchronously)
     * Stores new lift ride details in the data store
     * @param body Specify new Season value (required)
     * @param resortID ID of the resort the skier is at (required)
     * @param seasonID ID of the ski season (required)
     * @param dayID ID number of ski day in the ski season (required)
     * @param skierID ID of the skier riding the lift (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call writeNewLiftRideAsync(LiftRide body, Integer resortID, String seasonID, String dayID, Integer skierID, final ApiCallback<Void> callback) throws ApiException, ExecutionException, InterruptedException {
        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = writeNewLiftRideValidateBeforeCall(body, resortID, seasonID, dayID, skierID, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);

        return call;
    }
}
