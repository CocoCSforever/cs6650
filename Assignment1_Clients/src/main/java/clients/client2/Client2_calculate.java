package clients.client2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Client2_calculate {

    public static void main(String[] args) {
        String csvFile = "output.csv"; // Replace with the actual path to your CSV file

        long totalTime = 0;
        try (BufferedReader br0 = new BufferedReader(new FileReader(csvFile))) {
            int numberOfLines = countLines(br0)-1;
            // Reset the BufferedReader to start from the beginning
            br0.close();

            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            long[] responseTimes = new long[numberOfLines];
            int i = 0;


            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                // Split the line into fields using commas
                String[] fields = line.split(",");
                String latency = fields[2].substring(1);

                responseTimes[i] = Long.valueOf(latency);
                totalTime += responseTimes[i++];
            }

            Arrays.sort(responseTimes);
            System.out.println("mean response time (millisecs): " + totalTime/numberOfLines);
            System.out.println("median response time (millisecs): " + ((numberOfLines % 2 == 0) ? (responseTimes[numberOfLines / 2 - 1] + responseTimes[numberOfLines / 2]) / 2 : responseTimes[numberOfLines/2]));
            System.out.println("throughput = total number of requests/wall time (requests/second): " + numberOfLines/totalTime);
            System.out.println("p99 (99th percentile) response time: " + responseTimes[(int)(numberOfLines*0.99)-1]);
            System.out.println("min response time (millisecs): " + responseTimes[0]);
            System.out.println("max response time (millisecs): " + responseTimes[numberOfLines-1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countLines(BufferedReader br) throws IOException {
        int count = 0;
        while (br.readLine() != null) {
            count++;
        }
        return count;
    }
}


