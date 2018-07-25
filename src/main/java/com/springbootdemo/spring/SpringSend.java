package com.springbootdemo.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class SpringSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue simpleQueue(){
        return new Queue("spring_queue_demo",false);
    }

    public void simpleDemo() throws Exception {
        //发送消息
        rabbitTemplate.convertAndSend("spring_queue_demo","hello spring msg!");
        Thread.sleep(2*1000);
        //消费消息
        Message message = rabbitTemplate.receive("spring_queue_demo");
        String msg = new String(message.getBody(), "utf-8");
        System.out.println(msg);
    }

    public void sendDefault(){
        rabbitTemplate.convertAndSend("spring_queue_simple","hello spring simple msg!");
    }

    public void sendDirect(){
        rabbitTemplate.convertAndSend("spring_exchange_direct","spring_routing_direct","hello spring direct msg!");
    }

    public void sendFanout(){
        rabbitTemplate.convertAndSend("spring_exchange_fanout",null,"hello spring fanout msg!");
    }

    public void sendTopic(){
        rabbitTemplate.convertAndSend("spring_exchange_topic","spring.routing.topic.*","hello spring fanout msg!");
    }
}
