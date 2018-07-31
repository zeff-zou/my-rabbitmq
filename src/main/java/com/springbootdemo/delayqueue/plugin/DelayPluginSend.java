package com.springbootdemo.delayqueue.plugin;

import com.springbootdemo.utils.RabbitTemplateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DelayPluginSend {

    public void sendDelayPluginMq(){
        for (int i=0;i<3;i++){
            System.out.println( "send date:"+new Date());
            String context ="hello delay plugin message"+i;
            Integer n = (3-i)*10000;
            RabbitTemplateUtils.send("DELAY_PLUGIN_EXCHANGE","DELAY_PLUGIN_ROUTING",context, message -> {
                message.getMessageProperties().setDelay(n);
                return message;
            });
        }

    }

}
