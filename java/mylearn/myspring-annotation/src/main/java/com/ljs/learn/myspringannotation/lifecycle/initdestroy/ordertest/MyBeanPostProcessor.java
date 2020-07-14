package com.ljs.learn.myspringannotation.lifecycle.initdestroy.ordertest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    // BeanPostProcessor 接口实现
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor.postProcessBeforeInitialization, name = " + beanName);
        return bean;
    }

    // BeanPostProcessor 接口实现
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor.postProcessAfterInitialization, name = " + beanName);
        return bean;
    }
}
