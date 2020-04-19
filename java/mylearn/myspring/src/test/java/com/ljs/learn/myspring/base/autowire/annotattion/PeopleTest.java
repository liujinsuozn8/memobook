package com.ljs.learn.myspring.base.autowire.annotattion;

import com.ljs.learn.myspring.base.autowire.annotation.People;
import com.ljs.learn.myspring.base.autowire.annotation.People02;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PeopleTest {
    //测试@Autowire装配
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/autowire/annotation/bean.xml");
        People people = context.getBean("people", People.class);
        System.out.println(people.getCat());
        System.out.println(people.getDog());
    }

    //测试@Resource装配
    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/autowire/annotation/bean.xml");
        People02 people02 = context.getBean("people02", People02.class);
        System.out.println(people02.getCat().toString());
        System.out.println(people02.getDog().toString());
    }
}
