package com.ljs.learn.base.di.extension;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/di/extension/bean.xml");
        Student student01 = context.getBean("student01", Student.class);
        Student student02 = context.getBean("student02", Student.class);

        System.out.println(student01);
        System.out.println(student02);
    }
}
