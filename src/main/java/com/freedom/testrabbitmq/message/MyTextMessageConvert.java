package com.freedom.testrabbitmq.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

/**
 * @author ：wujie
 * @date ：Created in 2020/12/10 13:50
 * @description：
 * @version:
 */
public class MyTextMessageConvert implements MessageConverter {

    private static final SimpleMessageConverter convert=new SimpleMessageConverter();

    /**
     * 序列化
     * @param o
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        System.out.println("Text设置消息请求头......");
        messageProperties.setHeader("freedom","x2oa");
        return convert.toMessage(o,messageProperties);
    }

    /**
     * 反序列化
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object freedom = message.getMessageProperties().getHeaders().get("freedom");
        System.out.println("Text获得请求头......"+freedom);
        return convert.fromMessage(message);
    }
}
