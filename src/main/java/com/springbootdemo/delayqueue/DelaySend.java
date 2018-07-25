package com.springbootdemo.delayqueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DelaySend {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMq(){
        System.out.println( "send date:"+new Date());
        String expiration = String.valueOf(10 * 1000);//设置消息的过期时间
        String context ="hello delay message";
        rabbitTemplate.convertAndSend("DELAY_EXCHANGE","DELAY_ROUTING",context,message -> {
            message.getMessageProperties().setExpiration(expiration);
            return message;
        });
    }

}
