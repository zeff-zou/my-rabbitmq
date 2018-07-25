package com.springbootdemo.web;

import com.springbootdemo.spring.SpringSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring")
public class SpringMqController {
    @Autowired
    private SpringSend springSend;

    //发送简单队列消息
    @RequestMapping("/sendDemo")
    public String sendDemo() throws Exception {
        springSend.simpleDemo();
        return "success";
    }

    //发送简单队列消息
    @RequestMapping("/sendSimple")
    public String sendSimple(){
        springSend.sendDefault();
        return "success";
    }

    //发送Direct队列消息
    @RequestMapping("/sendDirect")
    public String sendDirect(){
        springSend.sendDirect();
        return "success";
    }

    //发送Fanout队列消息
    @RequestMapping("/sendFanout")
    public String sendFanout(){
        springSend.sendFanout();
        return "success";
    }

    //发送Direct队列消息
    @RequestMapping("/sendTopic")
    public String sendTopic(){
        springSend.sendTopic();
        return "success";
    }
}
