package cs6650;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class fileWriting {
    public static void main( String[] args ) throws InterruptedException {
        int THREAD_COUNT = 500, ITERATIONS_PER_THREAD = 1000;
        // Create and start 500 threads
        System.out.println(Thread.currentThread() + ":" + System.currentTimeMillis());
        Thread[] threads = new Thread[THREAD_COUNT];
        String[] outputs = new String[THREAD_COUNT*ITERATIONS_PER_THREAD];
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                // Generate and populate the outputs array
                for (int j = 0; j < ITERATIONS_PER_THREAD; j++) {
                    long timestamp = System.currentTimeMillis();
                    long threadId = Thread.currentThread().getId();
                    String output = timestamp + ", " + threadId + ", " + j + "\n";
                    outputs[threadIndex * ITERATIONS_PER_THREAD + j] = output;
                }
            });
            threads[i].start();
        }
        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (int j = 0; j < THREAD_COUNT*ITERATIONS_PER_THREAD; j++) {
                // Write the string to the file
                try {
                    writer.write(outputs[j]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread() + ":" + System.currentTimeMillis());
            System.out.println("Data has been written to output.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//for each thread, generate one + write
//        Thread[main,5,main]:1707238361113
//        Thread[main,5,main]:1707238361417

//for each thread, generate all + write
//        Thread[main,5,main]:1707238567314
//        Thread[main,5,main]:1707238567660

//        Thread[main,5,main]:1707238611901
//        Thread[main,5,main]:1707238612761

//        Thread[main,5,main]:1707238641544
//        Thread[main,5,main]:1707238641820

//for each thread, generate all strings
//after finishing all threads, write strings to txt
//        Thread[main,5,main]:1707239880688
//        Thread[main,5,main]:1707239880941

//        Thread[main,5,main]:1707240555567
//        Thread[main,5,main]:1707240555841
//
//        Thread[main,5,main]:1707240682674
//        Thread[main,5,main]:1707240682872