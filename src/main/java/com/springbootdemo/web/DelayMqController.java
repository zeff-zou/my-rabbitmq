package com.springbootdemo.web;

import com.springbootdemo.delayqueue.DelaySend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delay")
public class DelayMqController {
    @Autowired
    private DelaySend delaySend;

    //发送简单队列消息
    @RequestMapping("/sendDelayMq")
    public String sendDelayMq(){
        delaySend.sendDelayMq();
        return "success";
    }
}
