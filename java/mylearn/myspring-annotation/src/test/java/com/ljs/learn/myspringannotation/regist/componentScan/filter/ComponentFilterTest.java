package com.ljs.learn.myspringannotation.regist.componentScan.filter;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentFilterTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentFilterConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
