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

import clients.client1.Client1Runnable;
import clients.client2.Client2Runnable;
import io.swagger.client.model.SkierVertical;
import org.junit.Test;
import org.junit.Ignore;


import java.util.*;

import static clients.client1.Client1ThreadHelper.client1StartNThreads;
import static clients.client2.Client2ThreadHelper.client2StartNThreads;
import static org.junit.Assert.assertEquals;


/**
 * API tests for SkiersApi
 */
@Ignore
public class SkiersApiTest {

    private final SkiersApi api = new SkiersApi();

    /**
     * write a new lift ride for the skier
     *
     * get the total vertical for the skier for the specified ski day
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getSkierDayVerticalTest() throws Exception {
        Integer resortID = null;
        String seasonID = null;
        String dayID = null;
        Integer skierID = null;
        Integer response = api.getSkierDayVertical(resortID, seasonID, dayID, skierID);

        // TODO: test validations
    }
    /**
     * get the total vertical for the skier for specified seasons at the specified resort
     *
     * get the total vertical for the skier the specified resort. If no season is specified, return all seasons
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getSkierResortTotalsTest() throws Exception {
        Integer skierID = null;
        List<String> resort = null;
        List<String> season = null;
        SkierVertical response = api.getSkierResortTotals(skierID, resort, season);

        // TODO: test validations
    }
    /**
     * write a new lift ride for the skier
     *
     * Stores new lift ride details in the data store
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void writeNewLiftRideTest() throws Exception {

    }

    @Test
    public void client1SingleRequestTimeTest() throws Exception {
        long startTime = System.currentTimeMillis();
        client1StartNThreads(1, 10000);
        System.out.println("total run time (wall time) for 10000 request/thread to complete: " + 1.0*(System.currentTimeMillis()-startTime)/ Client1Runnable.getSuccessCounter());
    }

    @Test
    public void client2SingleRequestTimeTest() throws Exception {
        long startTime = System.currentTimeMillis();
        client2StartNThreads(20, 10000, "test.csv");
        System.out.println("total run time (wall time) for 10000 request/thread to complete: " + 1.0*(System.currentTimeMillis()-startTime)/ Client2Runnable.getSuccessCounter());
    }
}
