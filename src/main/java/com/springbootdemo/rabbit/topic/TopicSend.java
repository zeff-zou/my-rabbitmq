package com.springbootdemo.rabbit.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

@Component
public class TopicSend {

    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public void sendMq(String routing) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        // 声明exchange 交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //发送消息到交换机exchange
        String msg = "hello topic ";
        channel.basicPublish(EXCHANGE_NAME,routing,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
