package com.freedom.testrabbitmq.service;



import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author ：wujie
 * @date ：Created in 2020/2/6 18:34
 * @description： rabbit消费者
 * @version:
 */
@Component
//@RabbitListener(queues = "hello_queue")
//监听"hello_queue队列"
public class RabbitConsumer {

    private static final Logger log= LoggerFactory.getLogger(RabbitConsumer.class);



    //@RabbitHandler和类上的//@RabbitListener(queues = "hello_queue")一起使用，表示全局监听此队列
    //@RabbitListener(queues = "hello_queue")
    public void receive(String str, Channel channel, Message message){


        try {

            System.out.println("接收到消息：   "+str);

            //成功消费-手动消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("接收成功！！！");
        } catch (Exception e) {
            //消费失败消息处理
            try {
                //丢弃消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                log.info("接收失败！！！");
                //把消息重新丢进队列
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            } catch (Exception ex) {

            }

        }

    }

    //只监听死信队列,不监听延时队列
    @RabbitListener(queues ="dead_queue")
    public void delayReceive(String str, Channel channel, Message message){
        try {

            System.out.println("延时5s接收到消息：   "+str);

            //成功消费-手动消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("接收成功！！！");
        } catch (Exception e) {
            //消费失败消息处理
            try {
                //丢弃消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                log.info("接收失败！！！");
                //把消息重新丢进队列
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            } catch (Exception ex) {

            }

        }
    }


    //监听插件的延时队列
    @RabbitListener(queues ="delay.queue.demo.delay.queue")
    public void delayPluginsReceive(String str, Channel channel, Message message){
        try {

            System.out.println("延时10s接收到消息：   "+str);

            //成功消费-手动消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("接收成功！！！");
        } catch (Exception e) {
            //消费失败消息处理
            try {
                //丢弃消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                log.info("接收失败！！！");
                //把消息重新丢进队列
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            } catch (Exception ex) {

            }

        }
    }

}
