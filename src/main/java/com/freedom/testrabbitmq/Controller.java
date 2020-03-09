package com.freedom.testrabbitmq;

import com.alibaba.fastjson.JSON;
import com.freedom.testrabbitmq.service.RabbitProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：wujie
 * @date ：Created in 2020/2/6 19:51
 * @description：
 * @version:
 */
@RestController
public class Controller {

    @Resource
    private RabbitProvider rabbitProvider;

    @RequestMapping(method = RequestMethod.GET,value = "/testRabbit")
    public String test(){
        rabbitProvider.send();
        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/testRabbitByTime")
    public String testByTime(){
        rabbitProvider.sendByTime(5000);
        return "ok";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/testRabbitByPlugins")
    public String testByPlugins(){
        rabbitProvider.sendByPlugins(10000);
        return "ok";
    }

    public static void main(String[] args) {
        Result result = new Result("hahaha", "500", "good");
        Object o = JSON.toJSON(result);
        String string = JSON.toJSONString(result);
        System.out.println(string);
    }


}
