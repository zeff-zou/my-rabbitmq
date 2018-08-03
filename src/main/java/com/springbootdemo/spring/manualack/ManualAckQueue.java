package com.springbootdemo.spring.manualack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ManualAckQueue {

    private final static String SPRING_QUEUE_SIMPLE ="spring_queue_manualAck";
    private final static String SPRING_EXCHANGE_SIMPLE ="spring_exchange_manualAck";
    private final static String SPRING_ROUTING_DIRECT = "spring_routing_manualAck";

    private Integer messageNum = 0;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_SIMPLE, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_SIMPLE, durable = "true"),
            key = SPRING_ROUTING_DIRECT)
    })
    public void process(Message msg, Channel channel) throws IOException {
        try {
            System.out.println("spring_queue_manualAck: "+new String(msg.getBody(),"UTF-8"));
            if (true){  //todo 模拟处理消息时发生异常
                System.out.println("~`````````````````");
                throw new RuntimeException();
            }
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(),false); //确认消息
        }catch (Exception e){
            if (messageNum<3){
                messageNum++;
                //todo 假设前面处理失败的话这里使用NACK，不然RabbitMQ 不会再发送数据给它，因为 RabbitMQ 认为该服务的处理能力有限
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,true);      //否认消息
            }else {
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(),false);//拒绝消息,消息会被丢弃，不会重回队列
            }
        }
    }
}
