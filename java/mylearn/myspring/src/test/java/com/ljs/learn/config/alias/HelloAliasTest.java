package com.ljs.learn.config.alias;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloAliasTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("config/alias/bean.xml");
        Hello hello = (Hello) context.getBean("hello");
        Hello h1 = (Hello) context.getBean("h1");
        Hello h2 = (Hello) context.getBean("h2");
        Hello h3 = (Hello) context.getBean("h3");
        Hello h4 = (Hello) context.getBean("h4");
        Hello helloA = (Hello) context.getBean("helloA");
        Hello helloB = (Hello) context.getBean("helloB");

        System.out.println(hello);
        System.out.println(h1);
        System.out.println(h2);
        System.out.println(h3);
        System.out.println(h4);
        System.out.println(helloA);
        System.out.println(helloB);
    }
}
