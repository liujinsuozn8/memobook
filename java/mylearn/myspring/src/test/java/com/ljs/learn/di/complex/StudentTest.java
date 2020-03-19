package com.ljs.learn.di.complex;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("di/complex/bean.xml");
        Student student = (Student) context.getBean("student");
        // System.out.println(student.getName());
        System.out.println(student);
    }
}
