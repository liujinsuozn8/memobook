package com.ljs.learn.myspringannotation.scope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopConfigTest {
    // 单例scope测试
    @Test
    public void test01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("ioc 容器创建完成");
        Person personA = (Person) context.getBean("person");
        Person personB = (Person) context.getBean("person");

        assert (personA == personB);

        // 输出
        // singleton person create
        // ioc 容器创建完成
    }

    // 原型cope测试
    @Test
    public void test02(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("ioc 容器创建完成");
        Person personA = (Person) context.getBean("person02");
        Person personB = (Person) context.getBean("person02");

        assert (personA != personB);

        // 输出
        // singleton person create
        // ioc 容器创建完成
        // prototype person create: 1
        // prototype person create: 2
    }
}
