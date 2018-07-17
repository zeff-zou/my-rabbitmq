package com.springbootdemo.rabbit.routing.multiple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MultipleSend {
    private final static String EXCHANGE_NAME = "test_exchange_multiple";

    public void sendMq(String routing) throws IOException, TimeoutException {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        // 声明exchange 交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //发送消息到交换机exchange
        String msg = "hello multiple direct ";
        channel.basicPublish(EXCHANGE_NAME,routing,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
