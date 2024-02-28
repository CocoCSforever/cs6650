import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker {
    private final static String QUEUE_NAME = "hello1";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        If all the workers are busy, your queue can fill up.
//        You will want to keep an eye on that, and maybe add more workers,
//        or have some other strategy.
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try{
                doWork(message);
            } finally{
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        for (char ch: task.toCharArray()) {
            if (ch == '.') {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
