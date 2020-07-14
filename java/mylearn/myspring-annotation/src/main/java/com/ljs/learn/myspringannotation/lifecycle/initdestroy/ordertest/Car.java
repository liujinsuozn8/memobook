package com.ljs.learn.myspringannotation.lifecycle.initdestroy.ordertest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 测试初始化和销毁方法的执行顺序
public class Car implements InitializingBean, DisposableBean {
    public Car() {
        System.out.println("car constructor");
    }

    // DisposableBean接口实现
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.destroy");
    }

    // InitializingBean接口实现
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet");
    }

    // @Bean(initMethod="...")
    public void initOfBeanInitMethod(){
        System.out.println("@Bean InitMethod");
    }

    // @Bean(destroyMethod="...")
    public void destroyOfDestroyMethod(){
        System.out.println("@Bean DestroyMethod");
    }

    // JSR250注解测试
    @PostConstruct
    public void postConstruct(){
        System.out.println("JSR250 PostConstruct");
    }

    // JSR250注解测试
    @PreDestroy
    public void preDestroy(){
        System.out.println("JSR250 PreDestroy");
    }
}
