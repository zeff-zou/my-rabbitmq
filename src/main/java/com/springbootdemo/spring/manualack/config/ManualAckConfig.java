package com.springbootdemo.spring.manualack.config;

import com.springbootdemo.spring.manualack.ManualConsume;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ManualAckConfig {

    private final static String SPRING_QUEUE_SIMPLE_LISTENER ="spring_queue_manualAck_Listener";
    private final static String SPRING_EXCHANGE_SIMPLE_LISTENER ="spring_exchange_manualAck_Listener";
    private final static String SPRING_ROUTING_DIRECT_LISTENER = "spring_routing_manualAck_Listener";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Bean
    public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory, ManualConsume manualConsume) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(initQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(5);//最大并发消费者
        container.setConcurrentConsumers(2);//并发消费者
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(manualConsume);
        return container;
    }

    private Queue initQueue(){
        Queue queue = QueueBuilder.durable(SPRING_QUEUE_SIMPLE_LISTENER).build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(ExchangeBuilder.directExchange(SPRING_EXCHANGE_SIMPLE_LISTENER).durable().build());
        rabbitAdmin.declareBinding(new Binding(SPRING_QUEUE_SIMPLE_LISTENER,Binding.DestinationType.QUEUE,
                SPRING_EXCHANGE_SIMPLE_LISTENER,SPRING_ROUTING_DIRECT_LISTENER,new HashMap()));
        return queue;
    }
}
