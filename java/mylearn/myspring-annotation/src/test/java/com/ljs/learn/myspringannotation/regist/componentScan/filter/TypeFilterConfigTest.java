package com.ljs.learn.myspringannotation.regist.componentScan.filter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeFilterConfigTest {
    @Test
    public void testCustomTypeFilter(){
        ApplicationContext context = new AnnotationConfigApplicationContext(TypeFilterConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("name = " + name);
        }

        // 输出:
        // -----com.ljs.learn.myspringannotation.regist.componentScan.layers.controller.DemoController
        // -----com.ljs.learn.myspringannotation.regist.componentScan.layers.dao.DemoDao
        // -----com.ljs.learn.myspringannotation.regist.componentScan.layers.service.DemoService
        // name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // name = org.springframework.context.event.internalEventListenerProcessor
        // name = org.springframework.context.event.internalEventListenerFactory
        // name = typeFilterConfig   <<<<< 当前配置类
        // name = demoController
        // name = demoDao
        // name = demoService
    }
}
