package com.springbootdemo.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDefault(){
        rabbitTemplate.convertAndSend("spring_queue_simple","hello spring msg!");
    }

    public void sendDirect(){
        rabbitTemplate.convertAndSend("spring_exchange_direct","spring_routing_direct","hello spring direct msg!");
    }
}
