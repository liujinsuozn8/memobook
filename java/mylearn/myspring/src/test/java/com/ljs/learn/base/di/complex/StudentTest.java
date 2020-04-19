package com.ljs.learn.base.di.complex;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/di/complex/bean.xml");
        Student student = (Student) context.getBean("student");
        // System.out.println(student.getName());
        System.out.println(student);
    }
}
