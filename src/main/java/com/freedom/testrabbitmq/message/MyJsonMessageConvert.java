package com.freedom.testrabbitmq.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author ：wujie
 * @date ：Created in 2020/12/10 11:33
 * @description： 自定义消息转换器
 * @version:
 */
public class MyJsonMessageConvert implements MessageConverter  {

    private static final Jackson2JsonMessageConverter convert=new Jackson2JsonMessageConverter();

    /**
     * 消息序列化
     * @param o
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        System.out.println("Json设置消息请求头......");
        messageProperties.setHeader("freedom","x2oa");
        return convert.toMessage(o,messageProperties);
    }

    /**
     * 消息反序列化
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object freedom = message.getMessageProperties().getHeaders().get("freedom");
        System.out.println("Json获得请求头......"+freedom);
        return convert.fromMessage(message);
    }
}
