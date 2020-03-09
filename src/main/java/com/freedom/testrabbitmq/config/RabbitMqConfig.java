package com.freedom.testrabbitmq.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactoryUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ：wujie
 * @date ：Created in 2020/2/6 17:57
 * @description：rabbitmq配置
 * @version:
 */
@Configuration
public class RabbitMqConfig {
    private static  final  Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);

    private final String queueName="hello_queue";

    private final String exchangeName="hello_exchange";

    private final String helloKey="hello_rabbit";


    /**
     *死信队列 交换机，路由键标识符
     */
    private final String DEAD_LETTER_EXCHANGE="x-dead-letter-exchange";



    private final String DEAD_LETTER_ROUTE_kEY="x-dead-letter-routing-key";

    /**
     * 死信队列名
     */
    private static final String dead_Queue="dead_queue";

    /**
     * 死信交换机名
     */
    private static final String dead_Exchange="dead_exchange";

    /**
     * 死信路由键
     */
    private static final String dead_route_key="dead_route_key";

    /**
     * 队列超时时间标识符
     */
    private static final String MESSAGE_TTL="x-message-ttl";

    //使用插件定义延时队列 队列名，交换机名，路由键

    /*因为RabbitMQ只会检查第一个消息是否过期，
    如果过期则丢到死信队列，所以如果第一个消息的延时时长很长，
    而第二个消息的延时时长很短，则第二个消息并不会优先得到执行。*/



    private static final String DELAYED_QUEUE_NAME = "delay.queue.demo.delay.queue";
    private static final String DELAYED_EXCHANGE_NAME = "delay.queue.demo.delay.exchange";
    private static final String DELAYED_ROUTING_KEY = "delay.queue.demo.delay.routingkey";




    //定义死信队列，死信交换机，绑定关系

    @Bean
    public Queue deadQueue(){
        return new Queue(dead_Queue,true);
    }

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(dead_Exchange);
    }

    @Bean
    public Binding deadBinding(Queue deadQueue,DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(dead_route_key);
    }


    //自定义队列

    /**   Queue 可以有4个参数
     *      1.队列名
     *      2.durable       持久化消息队列 ,rabbitmq重启的时候不需要创建新的队列 默认true
     *      3.auto-delete   表示消息队列没有在使用时将被自动删除 默认是false
     *      4.exclusive     表示该消息队列是否只在当前connection生效,默认是false*/
    @Bean
    public Queue helloQueue(){
        //普通队列绑定死信交换机，设置超时时间成为延时队列
        HashMap<String, Object> map = new HashMap<>();
        map.put(DEAD_LETTER_EXCHANGE,dead_Exchange);
        map.put(DEAD_LETTER_ROUTE_kEY,dead_route_key);
        //设置队列里所有消息的过期时间ms
        map.put(MESSAGE_TTL,10000);
        //return new Queue(queueName);
        return new Queue(queueName,true,false,false,map);


        //以上设置是将hello_Queue的所有消息都延时6s
        //若需要控制每条消息的时间可在发送消息时设置延时时间

        //***若程序启动时报error Channel shutdown: channel error; protocol method: #method<channel.close>，则删掉服务器的队列重启程序***

    }

    /**
     * 自定义普通交换机
     */
    @Bean
    public DirectExchange helloExchange(){
        return new DirectExchange(exchangeName);
    }



    //插件定义延时队列

    @Bean
    public Queue immediateQueue() {


        return new Queue(DELAYED_QUEUE_NAME);
    }

    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        //topic/direct/fanout
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingNotify(@Qualifier("immediateQueue") Queue queue,
                                 @Qualifier("customExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }








    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //必须设置Mandatory为true,发送失败会返回信息给生产者(返回回调)
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 绑定交换机和队列
     */
    @Bean
    public Binding helloBinding(Queue helloQueue,DirectExchange helloExchange){
        return BindingBuilder.bind(helloQueue).to(helloExchange).with(helloKey);
    }


}
