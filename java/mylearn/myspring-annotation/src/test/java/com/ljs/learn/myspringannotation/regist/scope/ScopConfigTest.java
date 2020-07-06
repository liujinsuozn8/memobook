package com.ljs.learn.myspringannotation.regist.scope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopConfigTest {
    // 单例scope测试
    @Test
    public void testSingleton(){
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
    public void testPrototype(){
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

    // 懒加载测试
    @Test
    public void testLazy(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("ioc 容器创建完成");
        // 获取懒加载实例
        context.getBean("student");
        context.getBean("student");

        // 输出:
        // singleton person create
        // ioc 容器创建完成           <<<先创建容器
        // create student           <<<第一次获取实例时，创建对象
        // construct student, name=testStudent, age=22

        // 因为是单例的，所以只会在第一次获取时打印一次
    }
}
