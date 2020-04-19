package com.ljs.learn.myspring.base.di.beanScope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/di/beanScope/bean.xml");

        // 获取单例模式的bean
        Student studentS01 = context.getBean("studentS", Student.class);
        Student studentS02 = context.getBean("studentS", Student.class);

        // 输出：true
        System.out.println(studentS01 == studentS02);
    }

    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/di/beanScope/bean.xml");

        // 获取原型模式的bean
        Student studentP01 = context.getBean("studentP", Student.class);
        Student studentP02 = context.getBean("studentP", Student.class);

        // 输出：false
        System.out.println(studentP01 == studentP02);
    }
}
