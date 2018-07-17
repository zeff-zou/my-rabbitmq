package com.springbootdemo.rabbit.workqueues.fairdispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

@Component
public class FairSend {

    private final static String FAIR_QUEUE_NAME = "FAIR_QUEUE";

    public void sendMq() throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(FAIR_QUEUE_NAME, false, false, false, null);
        int prefetchCount = 1;
        //现在发送给同一个消费者不得超过一条消息
        channel.basicQos(prefetchCount);
        for (int i = 0; i < 10; i++) {
            // 消息内容
            String msg = "Fair Message " + i;
            channel.basicPublish("", FAIR_QUEUE_NAME, null, msg.getBytes());
            System.out.println("Send: '" + msg + "'");
        }
        channel.close();
        connection.close();
    }
}
