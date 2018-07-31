package com.springbootdemo.web;

import com.springbootdemo.delayqueue.DelaySend;
import com.springbootdemo.delayqueue.plugin.DelayPluginSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delay")
public class DelayMqController {
    @Autowired
    private DelaySend delaySend;
    @Autowired
    private DelayPluginSend delayPluginSend;

    //发送延迟消息(死信队列实现)
    @RequestMapping("/sendDelayMq")
    public String sendDelayMq(){
        delaySend.sendDelayMq();
        return "success";
    }

    //发送延迟消息(插件实现)
    @RequestMapping("/sendDelayPluginMq")
    public String sendDelayPluginMq(){
        delayPluginSend.sendDelayPluginMq();
        return "success";
    }
}
