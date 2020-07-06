package com.ljs.learn.myspringannotation.regist.componentScan;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanConfigTest {
    @Test
    public void tesConfigExclude(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component

        // 输出:
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        // componentScanConfig
        // demoDao
    }

    @Test
    public void tesConfigInclude(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig02.class);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component

        // 输出:
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        // componentScanConfig02
        // demoController
        // demoService
    }
}
