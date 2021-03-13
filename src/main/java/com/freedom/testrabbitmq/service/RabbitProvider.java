package com.freedom.testrabbitmq.service;

import com.freedom.testrabbitmq.config.RabbitMqConfig;
import com.freedom.testrabbitmq.model.Person;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wujie
 * @date ：Created in 2020/2/6 18:33
 * @description：rabbit生产者
 * @version:
 */
@Component
public class RabbitProvider {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(){

        Person person = new Person();
        person.setName("小明");
        person.setAge(25);
        rabbitTemplate.convertAndSend("hello_exchange","hello_rabbit",person);
    }

    public void sendByTime(long time){

        rabbitTemplate.convertAndSend("hello_exchange", "hello_rabbit", "hello world !!!", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置过期时间
                //若队列有设置全局消息则取较小值
                message.getMessageProperties().setExpiration(String.valueOf(time));

                return message;
            }
        });
    }

    /*public void sendByPlugins(long time){
        rabbitTemplate.convertAndSend("delay.queue.demo.delay.exchange","delay.queue.demo.delay.routingkey","hello world !!!",a->{
            a.getMessageProperties().setDelay(Integer.valueOf(String.valueOf(time)));
            return a;
        });
    }*/

    public void sendTestMessageConvert(){

        rabbitTemplate.convertAndSend("test-exchange","test-key","福利蛋");
    }

}
