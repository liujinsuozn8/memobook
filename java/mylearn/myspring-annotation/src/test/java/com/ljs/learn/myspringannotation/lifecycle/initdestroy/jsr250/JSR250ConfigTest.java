package com.ljs.learn.myspringannotation.lifecycle.initdestroy.jsr250;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JSR250ConfigTest {
    // 同时测试单实例和多实例Car
    @Test
    public void test01(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JSR250Config.class);

        System.out.println("容器创建完成");

        System.out.println("获取单实例bean");
        Object singleCar = context.getBean("singleCar");

        System.out.println("获取多实例bean");
        Object prototypeCar1 = context.getBean("prototypeCar");
        Object prototypeCar2 = context.getBean("prototypeCar");

        context.close();

        // 输出:
        // car init, name=singleCar
        // 容器创建完成
        // 获取单实例bean
        // 获取多实例bean
        // car init, name=prototypeCar
        // car init, name=prototypeCar
        // car destroy, name=singleCar
    }
}
