package com.ljs.learn.myspringannotation.lifecycle.initdestroy.ordertest;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class InitDestroyOrderConfigTest {

    @Test
    public void singleCar() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InitDestroyOrderConfig.class);
        System.out.println("容器创建完成");
        context.close();

        /*
        输出:
        BeanPostProcessor.postProcessBeforeInitialization, name = initDestroyOrderConfig
        BeanPostProcessor.postProcessAfterInitialization, name = initDestroyOrderConfig
        car constructor
        BeanPostProcessor.postProcessBeforeInitialization, name = singleCar
        JSR250 PostConstruct
        InitializingBean.afterPropertiesSet
        @Bean InitMethod
        BeanPostProcessor.postProcessAfterInitialization, name = singleCar
        容器创建完成
        JSR250 PreDestroy
        DisposableBean.destroy
        @Bean DestroyMethod
        */
    }
}