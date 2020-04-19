package com.ljs.learn.base.aop.annotation;

import com.ljs.learn.base.aop.annotation.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/aop/annotation/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.select();

        // 输出
        // around before
        // Log:before log
        // user select
        // around after
        // Log:after log
    }
}
