package com.springbootdemo.confirm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息确认 只要消息到了交换机(exchange)就返回ture,表示成功(注:消息只要到了exchange,哪怕没有到queue，这里返回的状态也是true)
 */
@Component
public class RabbitConfirmConfig implements RabbitTemplate.ConfirmCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识："+correlationData+";确认结果："+ack+";失败原因："+cause);
    }
}
