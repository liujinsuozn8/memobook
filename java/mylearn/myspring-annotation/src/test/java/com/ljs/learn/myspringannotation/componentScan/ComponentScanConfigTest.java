package com.ljs.learn.myspringannotation.componentScan;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanConfigTest {
    @Test
    public void tesConfig01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component
    }

    @Test
    public void tesConfig2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig02.class);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        // 输出结果中会包含DemoConfig自身，因为 @Configuration 本身就是一个 @Component
    }


}
