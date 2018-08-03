package com.springbootdemo.spring.manualack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class ManualConsume implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("spring_queue_manualAck_Listener"+new String(message.getBody(),"UTF-8"));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//手工返回ACK，通知此消息已经消费
    }
}
