package com.ljs.learn.myspring.base.ioc.springHello;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {
    @Test
    public void test01(){
        // 获取Spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("base/ioc/springHello/bean.xml");
        // 类配置到Spring之后，所有的对象都有Spring管理，使用时直接到上下文中获取
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }
}
