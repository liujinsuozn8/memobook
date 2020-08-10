package com.ljs.learn.myspringannotation.autowired;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class AwareConfigTest {
    @Test
    public void awareTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
        System.out.println(context);

        // 输出
        // bean name = myAware
        // name = awaretest, compute:11+22= 33
        // IOC = ...

        // 两个IOC容器的输出相同，是同一个对象
    }
}