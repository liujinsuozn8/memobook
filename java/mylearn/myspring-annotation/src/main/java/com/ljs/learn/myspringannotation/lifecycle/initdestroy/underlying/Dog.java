package com.ljs.learn.myspringannotation.lifecycle.initdestroy.underlying;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Dog implements ApplicationContextAware {
    private ApplicationContext context;
    // 实现ApplicationContextAware接口，获取IOC容器
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        System.out.println("set context");
    }
}
