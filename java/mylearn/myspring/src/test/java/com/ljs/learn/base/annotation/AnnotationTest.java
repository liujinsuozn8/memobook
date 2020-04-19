package com.ljs.learn.base.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationTest {
    // @Component测试
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/annotation/bean.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }

    // @Component("...")手动设置beanID测试
    @Test
    public void test02(){
        ApplicationContext context = new ClassPathXmlApplicationContext("base/annotation/bean.xml");
        // User02 myUesr02 = context.getBean("user02", User02.class);
        User02 myUesr02 = context.getBean("myUser02", User02.class);
        System.out.println(myUesr02.getName());
        System.out.println(myUesr02.getAge());
    }

    // @Scope测试
    @Test
    public void test03(){
        // User原型模式
        ApplicationContext context = new ClassPathXmlApplicationContext("base/annotation/bean.xml");
        User userA = context.getBean("user", User.class);
        User userB = context.getBean("user", User.class);

        // 输出：false
        System.out.println(userA == userB);
    }
}
