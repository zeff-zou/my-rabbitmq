package com.springbootdemo.spring;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringQueue {

    private final static String SPRING_QUEUE_SIMPLE ="spring_queue_simple";
    private final static String SPRING_EXCHANGE_SIMPLE ="spring_exchange_simple";

    //最简单的例子,默认是direct的类型，并且路由Key为空字符串
    @RabbitListener(bindings = {
        @QueueBinding(
                value = @Queue(value = SPRING_QUEUE_SIMPLE, durable = "true"),
                exchange = @Exchange(value = SPRING_EXCHANGE_SIMPLE, durable = "true"))
    })
    public void process(String msg){
        System.out.println("spring_queue_default: "+msg);
    }


    private final static String SPRING_QUEUE_DIRECT ="spring_queue_direct";
    private final static String SPRING_EXCHANGE_DIRECT ="spring_exchange_direct";
    private final static String SPRING_ROUTING_DIRECT = "spring_routing_direct";

    //Direct 例子
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_DIRECT, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_DIRECT, durable = "true"),
                    key = SPRING_ROUTING_DIRECT)//路由Key默认是空字符串
    })
    public void processDirect(String msg){
        System.out.println("spring_queue_default: "+msg);
    }
}
