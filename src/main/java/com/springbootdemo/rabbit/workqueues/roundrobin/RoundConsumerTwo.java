package com.springbootdemo.rabbit.workqueues.roundrobin;

import com.rabbitmq.client.*;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RoundConsumerTwo {
    private final static String ROUND_QUEUE_NAME = "ROUND_QUEUE";
    //开启监听
    public void consumer() throws IOException {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //定义一个消息的消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" ConsumerTwo '" + message + "'");
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        boolean autoAck = true; //消息的确认模式自动应答
        channel.basicConsume(ROUND_QUEUE_NAME, autoAck, consumer);
    }
}
