package com.ljs.learn.myspringannotation.componentScan.filter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeFilterConfigTest {
    @Test
    public void test01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(TypeFilterConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
