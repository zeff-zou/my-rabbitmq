package com.springbootdemo.spring;

import org.springframework.amqp.core.ExchangeTypes;
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

    //Direct 例子
    private final static String SPRING_QUEUE_DIRECT ="spring_queue_direct";
    private final static String SPRING_EXCHANGE_DIRECT ="spring_exchange_direct";
    private final static String SPRING_ROUTING_DIRECT = "spring_routing_direct";

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_DIRECT, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_DIRECT, durable = "true",type=ExchangeTypes.DIRECT),
                    key = SPRING_ROUTING_DIRECT)//路由Key默认是空字符串
    })
    public void processDirect(String msg){
        System.out.println("spring_queue_direct: "+msg);
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_DIRECT, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_DIRECT, durable = "true",type=ExchangeTypes.DIRECT),
                    key = SPRING_ROUTING_DIRECT)//路由Key默认是空字符串
    })
    public void processDirectTwo(String msg){
        System.out.println("spring_queue_direct_Two: "+msg);
    }

    //Fanout 例子
    private final static String SPRING_QUEUE_FANOUT_ONE ="spring_queue_fanout_one";
    private final static String SPRING_QUEUE_FANOUT_TWO ="spring_queue_fanout_two";
    private final static String SPRING_EXCHANGE_FANOUT ="spring_exchange_fanout";
    private final static String SPRING_ROUTING_FANOUT_ONE = "spring_routing_fanout_one";
    private final static String SPRING_ROUTING_FANOUT_TWO = "spring_routing_fanout_two";

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_FANOUT_ONE, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_FANOUT, durable = "true",type=ExchangeTypes.FANOUT),
                    key = SPRING_ROUTING_FANOUT_ONE)//FANOUT模式路由KEY其实是没有用的
    })
    public void processFanoutOne(String msg){
        System.out.println("spring_queue_fanout_one: "+msg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_FANOUT_TWO, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_FANOUT, durable = "true",type=ExchangeTypes.FANOUT),
                    key = SPRING_ROUTING_FANOUT_TWO)//路由Key默认是空字符串
    })
    public void processFanoutTwo(String msg){
        System.out.println("spring_queue_fanout_two: "+msg);
    }

    //Topic
    private final static String SPRING_QUEUE_TOPIC_ONE ="spring.queue.topic_one";
    private final static String SPRING_QUEUE_TOPIC_TWO ="spring.queue.topic_two";
    private final static String SPRING_EXCHANGE_TOPIC ="spring_exchange_topic";
    private final static String SPRING_ROUTING_TOPIC_ONE = "spring.routing.topic.one";
    private final static String SPRING_ROUTING_TOPIC_TWO = "spring.routing.topic.two";

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_TOPIC_ONE, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_TOPIC, durable = "true",type=ExchangeTypes.FANOUT),
                    key = SPRING_ROUTING_TOPIC_ONE)//路由Key默认是空字符串
    })
    public void processTopicOne(String msg){
        System.out.println("spring.queue.topic_one: "+msg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = SPRING_QUEUE_TOPIC_TWO, durable = "true"),
                    exchange = @Exchange(value = SPRING_EXCHANGE_TOPIC, durable = "true",type=ExchangeTypes.FANOUT),
                    key = SPRING_ROUTING_TOPIC_TWO)//路由Key默认是空字符串
    })
    public void processTopicTwo(String msg){
        System.out.println("spring.queue.topic_two: "+msg);
    }

}
