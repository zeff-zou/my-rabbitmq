package com.springbootdemo.rabbit.publishsubscribe;

import com.rabbitmq.client.*;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubscribeTwo {
    private final static String EXCHANGE_NAME = "test_exchange_fanout";
    private final static String QUEUE_NAME = "test_queue_two_fanout";

    public void subscribe() throws IOException {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定交换机和队列
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = new String(body, "UTF-8");
                System.out.println("SubscribeTwo:"+message);
                //手动确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
