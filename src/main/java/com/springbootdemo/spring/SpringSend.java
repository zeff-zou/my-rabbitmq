package com.springbootdemo.spring;

import com.springbootdemo.utils.RabbitTemplateUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


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
        RabbitTemplateUtils.send("spring_queue_demo","hello spring msg!");
        Thread.sleep(2*1000);
        //消费消息
        Message message = rabbitTemplate.receive("spring_queue_demo");
        String msg = new String(message.getBody(), "utf-8");
        System.out.println(msg);
    }

    public void sendDefault(){
        RabbitTemplateUtils.send("spring_queue_simple","hello spring simple msg!");
    }

    public void sendDirect(){
        RabbitTemplateUtils.send("spring_exchange_direct","spring_routing_direct","hello spring direct msg!");
    }

    public void sendFanout(){
        RabbitTemplateUtils.send("spring_exchange_fanout",null,"hello spring fanout msg!");
    }

    public void sendTopic(){
        RabbitTemplateUtils.send("spring_exchange_topic","spring.routing.topic.*","hello spring fanout msg!");
    }
}
