package com.springbootdemo.rabbit.workqueues.fairdispatch;

import com.rabbitmq.client.*;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FairConsumerTwo {
    private final static String FAIR_QUEUE_NAME = "FAIR_QUEUE";
    //开启监听
    public void consumer() throws IOException {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);//保证一次只分发一个
        //定义一个消息的消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    Thread.sleep(2000);
                    System.out.println("FairConsumerTwo:"+message);
                    //手动确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        boolean autoAck = false; //手动确认消息
        channel.basicConsume(FAIR_QUEUE_NAME, autoAck, consumer);
    }
}
