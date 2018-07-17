package com.springbootdemo.rabbit.publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class Publish {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public void sendMq() throws IOException, TimeoutException {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        channel.basicQos(1);//保证一次只分发一个
        // 声明exchange 交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //发送消息到交换机exchange
        for (int i = 0 ;i<10;i++){
            String msg = "hello publish subscribe ["+i+"]";
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
