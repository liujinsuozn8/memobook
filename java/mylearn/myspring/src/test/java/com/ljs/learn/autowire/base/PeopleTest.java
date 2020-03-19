package com.ljs.learn.autowire.base;

import com.ljs.learn.autowire.base.People;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PeopleTest {
    @Test
    public void testByName(){
        ApplicationContext context = new ClassPathXmlApplicationContext("autowire/base/bean.xml");
        People people = context.getBean("people", People.class);
        people.getCat().print();
        people.getDog().print();
    }

    @Test
    public void testByType(){
        ApplicationContext context = new ClassPathXmlApplicationContext("autowire/base/bean02.xml");
        People people = context.getBean("people", People.class);
        people.getCat().print();
        people.getDog().print();
    }
}
