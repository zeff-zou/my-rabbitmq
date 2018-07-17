package com.springbootdemo.web;

import com.springbootdemo.rabbit.simplequeue.SimpleConsumer;
import com.springbootdemo.rabbit.simplequeue.SimpleSender;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairConsumerOne;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairConsumerTwo;
import com.springbootdemo.rabbit.workqueues.fairdispatch.FairSend;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundConsumerOne;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundConsumerTwo;
import com.springbootdemo.rabbit.workqueues.roundrobin.RoundSend;
import org.springframework.beans.factory.annotation.Autowired;
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
}
