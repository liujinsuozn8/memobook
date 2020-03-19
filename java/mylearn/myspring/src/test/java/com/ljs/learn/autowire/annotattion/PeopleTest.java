package com.ljs.learn.autowire.annotattion;

import com.ljs.learn.autowire.annotation.People;
import com.ljs.learn.autowire.annotation.People02;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PeopleTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("autowire/annotation/bean.xml");
        People people = context.getBean("people", People.class);
        System.out.println(people.getCat().toString());
        System.out.println(people.getDog().toString());
    }

    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("autowire/annotation/bean.xml");
        People02 people02 = context.getBean("people02", People02.class);
        System.out.println(people02.getCat().toString());
        System.out.println(people02.getDog().toString());
    }
}
