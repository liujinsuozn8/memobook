package com.ljs.learn.myspringannotation.extension.ex02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.extension.ex02")
public class Ex02Config {

    @Bean
    public Object obj01(){
        System.out.println("obj01 init");
        return new Object();
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Ex02Config.class);
        context.close();

        // MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry  count = 8
        // MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory  count = 9
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.annotation.internalCommonAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        // ex02Config
        // myBeanDefinitionRegistryPostProcessor
        // obj01
        // car
        // obj01 init
    }
}
