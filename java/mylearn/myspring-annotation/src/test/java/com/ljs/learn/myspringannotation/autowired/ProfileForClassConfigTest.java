package com.ljs.learn.myspringannotation.autowired;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

// @Profile 装饰 配置类的测试
public class ProfileForClassConfigTest {
    // 测试dev环境
    @Test
    public void devEnvTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(ProfileForClassConfig.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 输出
        // profileForClassConfig
        // devEnv
        // otherEnv
    }

    // 测试product环境
    @Test
    public void productEnvTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("product");
        context.register(ProfileForClassConfig.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 没有配置类的输出
    }
}