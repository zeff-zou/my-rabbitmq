package com.springbootdemo.rabbit.workqueues.Roundrobin;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springbootdemo.rabbit.ConnectionUtils;
import org.springframework.stereotype.Component;

@Component
public class RoundSend {

    private final static String ROUND_QUEUE_NAME = "ROUND_QUEUE";

    public void sendMq() throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(ROUND_QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 10; i++) {
        // 消息内容
            String msg = "." + i;
            channel.basicPublish("", ROUND_QUEUE_NAME, null, msg.getBytes());
            System.out.println("Send: '" + msg + "'");
        }
        channel.close();
        connection.close();
    }
}
