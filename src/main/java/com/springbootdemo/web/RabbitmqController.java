package com.springbootdemo.web;

import com.springbootdemo.rabbit.simplequeue.SimpleConsumer;
import com.springbootdemo.rabbit.simplequeue.SimpleSender;
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

    //发送简单队列消息
    @RequestMapping("/sendSimple")
    public String sendSimple() throws IOException, TimeoutException {
        simpleSender.sendMq();
        return "success";
    }
    //开启监听简单队列消息
    @RequestMapping("/sendConsumer")
    public String sendConsumer() throws IOException, TimeoutException {
        simpleConsumer.consumer();
        return "success";
    }

    @RequestMapping("/hello1")
    public String sendQueue() {

        return "Hello World";
    }
}
