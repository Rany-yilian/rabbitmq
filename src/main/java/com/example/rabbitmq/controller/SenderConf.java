package com.example.rabbitmq.controller;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SenderConf{

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue queue(){
        return new Queue("queue-test",true);
    }

}