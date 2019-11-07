package com.example.rabbitmq.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class HelloController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping(value="/hello",produces = "text/plain;charset=UTF-8")
    public String index(String message){

        String context = "hello " + new Date();
        System.out.println(message);
        System.out.println("Sender : " + context);
        //这里是消息确认
        this.rabbitTemplate.convertAndSend("queue-test",message);

        return "index111";
    }
}