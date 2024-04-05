package clients.client2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Client2_calculate {
    public static void main(String[] args) {
        String csvFile = "output.csv";

        long totalResponseTime = 0;
        long totalWallTime = 0;
        try (BufferedReader br0 = new BufferedReader(new FileReader(csvFile))) {
            int numberOfLines = countLines(br0)-2;
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
                if(fields.length == 1){
                    totalWallTime = Long.valueOf(fields[0]);
                    break;
                }
//                System.out.println(i);
                try {
                    String latency = fields[2].substring(1);
                    responseTimes[i] = Long.valueOf(latency);
                    totalResponseTime += responseTimes[i++];
                } catch ( Exception e){
                    System.out.println("line " + i + ": " + line);
                }
            }

            Arrays.sort(responseTimes);
            System.out.println("mean response time (millisecs): " + totalResponseTime/numberOfLines);
            System.out.println("median response time (millisecs): " + ((numberOfLines % 2 == 0) ? (responseTimes[numberOfLines / 2 - 1] + responseTimes[numberOfLines / 2]) / 2 : responseTimes[numberOfLines/2]));
            System.out.println("throughput = total number of requests/wall time (requests/second): " + 1.0*numberOfLines/totalWallTime*1000);
            System.out.println("p99 (99th percentile) response time: " + responseTimes[(int)(numberOfLines*0.99)-1]);
            System.out.println("min response time (millisecs): " + responseTimes[0]);
            System.out.println("max response time (millisecs): " + responseTimes[numberOfLines-1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calculate(long totalWallTime) {
        String csvFile = "output.csv";

        long totalResponseTime = 0;
        try (BufferedReader br0 = new BufferedReader(new FileReader(csvFile))) {
            int numberOfLines = countLines(br0)-2;
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
                if(fields.length == 1){
                    break;
                }
//                System.out.println(i);
                try {
                    String latency = fields[2].substring(1);
                    responseTimes[i] = Long.valueOf(latency);
                    totalResponseTime += responseTimes[i++];
                } catch ( Exception e){
                    System.out.println("line " + i + ": " + line);
                }

//                System.out.println(responseTimes[i-1]);
//                System.out.println("record #" + i + ": " + totalTime);
            }

            Arrays.sort(responseTimes);
            System.out.println(numberOfLines);
            System.out.println(totalWallTime);
            System.out.println("mean response time (millisecs): " + totalResponseTime/numberOfLines);
            System.out.println("median response time (millisecs): " + ((numberOfLines % 2 == 0) ? (responseTimes[numberOfLines / 2 - 1] + responseTimes[numberOfLines / 2]) / 2 : responseTimes[numberOfLines/2]));
            System.out.println("throughput = total number of requests/wall time (requests/second): " + 1.0*numberOfLines/totalWallTime*1000);
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


