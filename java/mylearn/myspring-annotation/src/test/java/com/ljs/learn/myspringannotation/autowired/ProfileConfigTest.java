package com.ljs.learn.myspringannotation.autowired;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class ProfileConfigTest {

    // 不指定激活环境，将会使用 default 环境
    @Test
    public void defaultEnvTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProfileConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        // 输出:
        // profileConfig
        // defaultEnv
        // otherEnv
    }

    // 通过命令行参数来设置环境
    // -Dspring.profiles.active=test
    @Test
    public void VMoptionTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProfileConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        // 输出:
        // profileConfig
        // testEnv
        // otherEnv
    }

    // 在容器中设置需要激活的环境
    @Test
    public void productEnvTest(){
        // 1. 创建空的IOC容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 2. 设置需要激活的环境，可以一次设置多个
        context.getEnvironment().setActiveProfiles("product", "dev");

        // 3. 注册配置类
        context.register(ProfileConfig.class);

        // 4. 刷新容器
        context.refresh();

        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        // 输出:
        // profileConfig
        // devEnv
        // productEnv
        // otherEnv
    }

}