package com.ljs.learn.myspringannotation.lifecycle.initdestroy.underlying;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UnderlyingConfigTest {
    @Test
    public void dog(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UnderlyingConfig.class);
        System.out.println("容器创建完成");
        Object dog = context.getBean("dog");
    }

}