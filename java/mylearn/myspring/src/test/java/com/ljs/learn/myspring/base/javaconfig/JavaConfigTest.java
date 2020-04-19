package com.ljs.learn.myspring.base.javaconfig;

import com.ljs.learn.myspring.base.javaconfig.bean.Student;
import com.ljs.learn.myspring.base.javaconfig.bean.User;
import com.ljs.learn.myspring.base.javaconfig.config.MyConfig;
import com.ljs.learn.myspring.base.javaconfig.config.SubConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigTest {
    // 主配置类测试
    @Test
    public void test01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        // 测试主配置类中配置的bean
        User user = context.getBean("user", User.class);
        System.out.println(user.getName());

        // 测试子配置类中配置中的bean
        Student student = context.getBean("student", Student.class);
        System.out.println(student.getName());
    }

    // 子配置类测试：获取使用@Component装饰的类
    @Test
    public void test02(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SubConfig.class);
        Student student = context.getBean("student", Student.class);
        System.out.println(student.getName());
    }

    // 子配置类测试：获取没有使用@Component装饰的类
    @Test
    public void test03(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SubConfig.class);
        User user = context.getBean("user", User.class);
        System.out.println(user.getName());

        // 异常：org.springframework.beans.factory.NoSuchBeanDefinitionException
        //      : No bean named 'user' available
    }
}
