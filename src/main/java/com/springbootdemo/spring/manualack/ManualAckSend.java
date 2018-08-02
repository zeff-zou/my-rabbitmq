package com.springbootdemo.spring.manualack;

import com.springbootdemo.utils.RabbitTemplateUtils;
import org.springframework.stereotype.Component;

@Component
public class ManualAckSend {

    public void sendMq(){
        RabbitTemplateUtils.send("spring_exchange_manualAck","spring_routing_manualAck","hello spring simple msg!");
    }
}
