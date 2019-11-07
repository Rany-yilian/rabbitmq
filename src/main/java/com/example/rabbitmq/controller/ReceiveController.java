package com.example.rabbitmq.controller;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Date;

@Component
public class ReceiveController {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    //这里是消息确认
    @RabbitListener(queues = "queue-test")
    public void process(Message message, Channel channel) throws IOException {
        //采用手动应答模式，手动确认应答更为安全稳定
        try{

            //消息确认
            //false只确认当前一个消息收到，true确认所有consumer获得消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receive:"+new String(message.getBody()));

        }catch (Exception e){
            e.printStackTrace();
            if (message.getMessageProperties().getRedelivered()) {
                System.out.println("异常--消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
            } else {
                System.out.println("异常--消息即将再次返回队列处理...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
            }
        }

    }
}