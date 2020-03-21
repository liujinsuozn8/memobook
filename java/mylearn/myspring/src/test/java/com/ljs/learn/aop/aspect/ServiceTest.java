package com.ljs.learn.aop.aspect;

import com.ljs.learn.aop.aspect.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
    @Test
    public void tet01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aop/aspect/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.insert();
        userService.select();
        userService.update();
        userService.delete();
    }
}
