package com.ljs.learn.myspringannotation.lifecycle.initdestroy.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class InitDestroyLifecycleConfig {
    // 单实例bean
    // 指定初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car singleCar(){
        return new Car();
    }

    // 多实例bean
    @Scope("prototype")
    // 指定初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car prototypeCar(){
        return new Car();
    }
}
