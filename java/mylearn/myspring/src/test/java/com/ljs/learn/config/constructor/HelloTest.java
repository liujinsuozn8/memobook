package com.ljs.learn.config.constructor;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloTest {
    @Test
    public void test(){
        // 分别通过Id和别名来获取bean，检验这些对象是否是同一个对象
        ApplicationContext context = new ClassPathXmlApplicationContext("config/constructor/bean.xml");
        Hello hello01 = (Hello) context.getBean("hello1");
        Hello hello02 = (Hello) context.getBean("hello2");
        Hello hello03 = (Hello) context.getBean("hello3");

        System.out.println(hello01);
        System.out.println(hello02);
        System.out.println(hello03);
    }
}
