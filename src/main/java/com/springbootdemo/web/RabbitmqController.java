package com.springbootdemo.web;

import com.springbootdemo.rabbit.publishsubscribe.Publish;
import com.springbootdemo.rabbit.publishsubscribe.SubscribeOne;
import com.springbootdemo.rabbit.publishsubscribe.SubscribeTwo;
import com.springbootdemo.rabbit.routing.direct.DirectConsumer;
import com.springbootdemo.rabbit.routing.direct.DirectSend;
import com.springbootdemo.rabbit.routing.multiple.MultipleConsumer;
import com.springbootdemo.rabbit.routing.multiple.MultipleSend;
import com.springbootdemo.rabbit.simplequeue.SimpleConsumer;
import com.springbootdemo.rabbit.simplequeue.SimpleSender;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairConsumerOne;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairConsumerTwo;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairSend;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundConsumerOne;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundConsumerTwo;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zzf on 2017/3/7.
 */
@RestController
public class RabbitmqController {
    @Autowired
    private SimpleSender simpleSender;
    @Autowired
    private SimpleConsumer simpleConsumer;
    @Autowired
    private RoundSend roundSend;
    @Autowired
    private RoundConsumerOne roundConsumerOne;
    @Autowired
    private RoundConsumerTwo roundConsumerTwo;
    @Autowired
    private FairSend fairSend;
    @Autowired
    private FairConsumerOne fairConsumerOne;
    @Autowired
    private FairConsumerTwo fairConsumerTwo;
    @Autowired
    private Publish publish;
    @Autowired
    private SubscribeOne subscribeOne;
    @Autowired
    private SubscribeTwo subscribeTwo;
    @Autowired
    private DirectSend directSend;
    @Autowired
    private DirectConsumer directConsumer;
    @Autowired
    private MultipleSend multipleSend;
    @Autowired
    private MultipleConsumer multipleConsumer;

    //发送简单队列消息
    @RequestMapping("/sendSimple")
    public String sendSimple() throws IOException, TimeoutException {
        simpleSender.sendMq();
        return "success";
    }
    //开启监听简单队列消息
    @RequestMapping("/consumer")
    public String consumer() throws IOException, TimeoutException {
        simpleConsumer.consumer();
        return "success";
    }

    //发送工作队列消息(轮询分发)
    @RequestMapping("/sendRound")
    public String sendRound() throws Exception {
        roundSend.sendMq();
        return "success";
    }

    //开启监听工作队列(轮询分发)
    @RequestMapping("/roundConsumer")
    public String roundConsumer() throws Exception {
        roundConsumerOne.consumer();
        roundConsumerTwo.consumer();
        return "success";
    }

    //发送工作队列消息(公平分发)
    @RequestMapping("/sendFair")
    public String sendFair() throws Exception {
        fairSend.sendMq();
        return "success";
    }

    //开启监听工作队列(公平分发)
    @RequestMapping("/fairConsumer")
    public String fairConsumer() throws IOException {
        fairConsumerOne.consumer();
        fairConsumerTwo.consumer();
        return "success";
    }

    //发送订阅消息
    @RequestMapping("/sendPublish")
    public String sendPublish() throws Exception {
        publish.sendMq();
        return "success";
    }

    //开启监听(分发订阅)
    @RequestMapping("/Subscribe")
    public String Subscribe() throws IOException {
        subscribeOne.subscribe();
        subscribeTwo.subscribe();
        return "success";
    }


    //发送路由direct队列消息 todo 路由目前定义了三种 orange black green
    @RequestMapping("/sendDirect")
    public String sendDirect(String routing) throws Exception {
        directSend.sendMq(routing);
        return "success";
    }
    //开启监听(路由direct)
    @RequestMapping("/directConsumer")
    public String directConsumer() throws Exception {
        directConsumer.queueOne();
        directConsumer.queueTwo();
        return "success";
    }

    //发送路由direct队列消息
    @RequestMapping("/sendMultiple")
    public String sendMultiple() throws Exception {
        multipleSend.sendMq("black");
        return "success";
    }
    //开启监听(路由direct)
    @RequestMapping("/multipleConsumer")
    public String multipleConsumer() throws Exception {
        multipleConsumer.queueOne();
        multipleConsumer.queueTwo();
        return "success";
    }
}
