package com.springbootdemo.utils;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitTemplateUtils {

    static RabbitTemplate template;

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        RabbitTemplateUtils.template = template;
    }

    public static void send(String routingKey, String message){
        template.convertAndSend("",routingKey, message,getCorrelationData());
    }

    public static void send(String exchange, String routingKey, String message) {
        template.convertAndSend(exchange, routingKey, message,getCorrelationData());
    }

    public static void send(String exchange, String routingKey, String message,MessagePostProcessor messagePostProcessor) {
        template.convertAndSend(exchange, routingKey, message,messagePostProcessor,getCorrelationData());
    }


    private static CorrelationData getCorrelationData(){
        UUID uuid = UUID.randomUUID();
        return new CorrelationData(uuid.toString());
    }
}
