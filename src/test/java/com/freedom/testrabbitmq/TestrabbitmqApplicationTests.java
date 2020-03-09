package com.freedom.testrabbitmq;


import com.freedom.testrabbitmq.service.RabbitProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestrabbitmqApplicationTests {


    @Resource
    private RabbitProvider rabbitProvider;

    @Test
    public void test(){
        rabbitProvider.send();
    }

}
