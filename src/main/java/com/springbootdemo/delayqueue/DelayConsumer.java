package com.springbootdemo.delayqueue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;

@Component
public class DelayConsumer {

    private final static String DELAY_QUEUE = "DELAY_QUEUE";//缓存队列(死信队列)
    private final static String DELAY_ROUTINGE = "DELAY_ROUTING";//缓存队列路由key

    private final static String DELAY_CUSTOM_QUEUE = "DELAY_CUSTOM_QUEUE";//实际消费队列
    private final static String DELAY_CUSTOM_ROUTING = "DELAY_CUSTOM_ROUTING";//实际消费队列路由key

    private final static String DELAY_EXCHANGE = "DELAY_EXCHANGE";//交换器

    //实际消费队列
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(value = DELAY_CUSTOM_QUEUE,durable = "true"),
                            exchange = @Exchange(value = DELAY_EXCHANGE,durable = "true"),
                            key = DELAY_CUSTOM_ROUTING
                    )
            }
    )
    public void process(String message){
        System.out.println("Message:"+message);
        System.out.println("receive date:"+new Date());
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void initDelayCustomQueue(){
        rabbitAdmin.declareQueue( QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE)//DLX deal letter(死信后转发的交换器)
                .withArgument("x-dead-letter-routing-key", DELAY_CUSTOM_ROUTING)//死信后携带的路由
//                .withArgument("x-message-ttl",3000) todo 设置整个队列的过期时间(本例子没有用到,因为在发送消息时,设置了消息的过期时间)
                .build());
        rabbitAdmin.declareExchange(ExchangeBuilder.directExchange(DELAY_EXCHANGE).durable().build());
        rabbitAdmin.declareBinding(new Binding(DELAY_QUEUE,Binding.DestinationType.QUEUE,
                DELAY_EXCHANGE,DELAY_ROUTINGE,new HashMap()));
    }

    //    //缓存队列(死信队列)
//    @Bean
//    public org.springframework.amqp.core.Queue delayCustomQueue(){
//        return
//
//
//
//    }
//    //缓存队列使用的交换机
//    @Bean
//    public org.springframework.amqp.core.Exchange delayExchange() {
//        return ExchangeBuilder.directExchange(DELAY_EXCHANGE).durable().build();
//    }
//
//    //将缓存队列和交换机以及路由key绑定起来
//    @Bean
//    public Binding myExchangeBinding(@Qualifier("delayExchange") org.springframework.amqp.core.Exchange directExchange,
//                                     @Qualifier("delayCustomQueue") org.springframework.amqp.core.Queue queue) {
//        return BindingBuilder.bind(queue).to(directExchange).with(DELAY_ROUTINGE).noargs();
//    }

}
