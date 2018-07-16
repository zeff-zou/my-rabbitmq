package com.springbootdemo.rabbit.simplequeue;

import com.rabbitmq.client.*;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleConsumer {

    private static final String SIMPLE_QUEUE_NAME = "SIMPLE_QUEUE";

    //开启监听
    public void consumer() throws IOException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();
        //从链接中创建通道
        Channel channel = connection.createChannel();
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties
                    properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Received '" + message + "'");
            }
        };
        //监听队列
        channel.basicConsume(SIMPLE_QUEUE_NAME, true, consumer);
    }
}
