package com.springbootdemo.rabbit.simplequeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class SimpleSender {

    private static final String SIMPLE_QUEUE_NAME = "SIMPLE_QUEUE";

    public void sendMq() throws IOException, TimeoutException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();
        //从链接中创建通道
        Channel channel = connection.createChannel();
        //创建队列
        boolean durable = false;//队列是否持久化
        boolean exclusive = false;//是否独占队列
        boolean autoDelete = false;//是否自动删除
        channel.queueDeclare(SIMPLE_QUEUE_NAME,durable,exclusive,autoDelete,null);

        String msg = "holle simple queue";
        channel.basicPublish("",SIMPLE_QUEUE_NAME,null,msg.getBytes());
        System.out.println("~~~~~~~~~~~~~~~ send ms :"+msg);
        channel.close();
        connection.close();
    }
}
