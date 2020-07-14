package com.ljs.learn.myspringannotation.lifecycle.initdestroy.bean;

import com.ljs.learn.myspringannotation.lifecycle.initdestroy.bean.Car;
import com.ljs.learn.myspringannotation.lifecycle.initdestroy.bean.InitDestroyLifecycleConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InitDestroyLifecycleConfigTest {
    // 单例测试
    @Test
    public void car01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InitDestroyLifecycleConfig.class);
        System.out.println("容器创建完成");
        System.out.println("---------car01----------");
        Car car01 = (Car) context.getBean("singleCar");

        System.out.println("---------car02----------");
        Car car02 = (Car) context.getBean("singleCar");

        System.out.println("关闭容器");
        context.close();

        // 输出:
        // car constructor
        // car init
        // 容器创建完成
        // ---------car01----------
        // ---------car02----------
        // 关闭容器
        // car destroy
    }
    // 多实例bean测试
    @Test
    public void car02() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InitDestroyLifecycleConfig.class);
        System.out.println("容器创建完成");
        System.out.println("---------car01----------");
        Car car01 = (Car) context.getBean("prototypeCar");

        System.out.println("---------car02----------");
        Car car02 = (Car) context.getBean("prototypeCar");

        System.out.println("关闭容器");
        context.close();

        // 输出
        // car constructor         <<<<<< singleCar的输出
        // car init                <<<<<< singleCar的输出
        // 容器创建完成
        // ---------car01----------
        // car constructor
        // car init
        // ---------car02----------
        // car constructor
        // car init
        // 关闭容器
        // car destroy             <<<<<< singleCar的输出
    }
}