package com.freedom.testrabbitmq.listener;

import com.alibaba.fastjson.JSON;
import com.freedom.testrabbitmq.model.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ：wujie
 * @date ：Created in 2020/12/10 14:09
 * @description：
 * @version:
 */
@Component
public class TestMyMessageConvert {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "test-queue"),
            exchange = @Exchange(name = "test-exchange"),
            key = "test-key"))
    public void listener(String str, Message message){

        System.out.println(str);
        System.out.println(new String(message.getBody()));

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int[] ints = new int[10];
        ints[0]=ints[0]+1;
        System.out.println(ints[0]);

    }


}
