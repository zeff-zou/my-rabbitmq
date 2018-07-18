package com.springbootdemo.rabbit.topic;

import com.rabbitmq.client.*;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TopicConsumer {

    private final static String QUEUE_NAME_ONE="test_queue_topic_one";
    private final static String QUEUE_NAME_TWO="test_queue_topic_two";

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public void consumerOne() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME_ONE, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME_ONE, EXCHANGE_NAME, "item.#");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达 触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("consumerOne:" + msg);
                // 手动回执
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME_ONE, autoAck, consumer);
    }

    public void consumerTwo() throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME_TWO, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME_TWO, EXCHANGE_NAME, "item.update");
        channel.queueBind(QUEUE_NAME_TWO, EXCHANGE_NAME, "item.delete");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达 触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("consumerTwo:" + msg);
                // 手动回执
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME_TWO, autoAck, consumer);
    }

}
