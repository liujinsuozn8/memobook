package com.ljs.learn.myspring.base.aop.xml;

import com.ljs.learn.myspring.base.aop.xml.service.UserService;
import com.ljs.learn.myspring.base.aop.xml.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
    // 通过抽象类型来获取bean
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/aop/xml/bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.select();
        userService.delete();
        userService.update();
        userService.insert();
    }

    // 通过实现类的类型来获取bean
    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/aop/xml/bean.xml");
        UserServiceImpl userService = context.getBean("userService", UserServiceImpl.class);
        //异常：
        // BeanNotOfRequiredTypeException:
        //     Bean named 'userService'
        //     is expected to be of type
        //     'com.ljs.learn.myspring.base.aop.xml.service.UserServiceImpl'
        //     but was actually of type 'com.sun.proxy.$Proxy7'
    }
}
