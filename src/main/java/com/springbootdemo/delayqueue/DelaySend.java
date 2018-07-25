package com.springbootdemo.delayqueue;

import com.springbootdemo.utils.RabbitTemplateUtils;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class DelaySend {

    public void sendDelayMq(){
        System.out.println( "send date:"+new Date());
        String expiration = String.valueOf(10 * 1000);//设置消息的过期时间
        String context ="hello delay message";
        RabbitTemplateUtils.send("DELAY_EXCHANGE","DELAY_ROUTING",context,message -> {
            message.getMessageProperties().setExpiration(expiration);
            return message;
        });
    }

}
