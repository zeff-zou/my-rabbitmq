//package com.springbootdemo.delayqueue.plugin;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.CustomExchange;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class DelayPluginConsumer {
//
//    public static final String DELAY_PLUGIN_QUEUE = "DELAY_PLUGIN_QUEUE";
//    public static final String DELAY_PLUGIN_EXCHANGE = "DELAY_PLUGIN_EXCHANGE";
//    private final static String DELAY_PLUGIN_ROUTING = "DELAY_PLUGIN_ROUTING";
//
//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    @PostConstruct
//    public void init(){
//        //创建队列
//        rabbitAdmin.declareQueue(QueueBuilder.durable(DELAY_PLUGIN_QUEUE).build());
//        //创建交换机(由于Spring AMQP中没有这个类型的交换机，所以我们使用一个CustomExchange来定义这个插件构建的交换机)
//        Map<String, Object> args = new HashMap<String, Object>();
//        args.put("x-delayed-type", "direct");
//        rabbitAdmin.declareExchange(new CustomExchange(DELAY_PLUGIN_EXCHANGE, "x-delayed-message", true, false, args));
//        //绑定交换机与队列
//        rabbitAdmin.declareBinding(new Binding(DELAY_PLUGIN_QUEUE,Binding.DestinationType.QUEUE,
//                DELAY_PLUGIN_EXCHANGE,DELAY_PLUGIN_ROUTING,new HashMap()));
//    }
//
//
//    //监听消费队列
//    @RabbitListener(queues  = DELAY_PLUGIN_QUEUE)
//    public void process(String message){
//        System.out.println("Message:"+message);
//        System.out.println("receive date:"+new Date());
//    }
//}
