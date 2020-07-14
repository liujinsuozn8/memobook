package com.ljs.learn.myspringannotation.lifecycle.initdestroy.ordertest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 测试初始化和销毁方法的执行顺序
@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.lifecycle.initdestroy.ordertest")
public class InitDestroyOrderConfig {
    @Bean(initMethod = "initOfBeanInitMethod", destroyMethod = "destroyOfDestroyMethod")
    public Car singleCar(){
        return new Car();
    }

    // @Bean
    // public MyBeanPostProcessor myBeanPostProcessor(){
    //     return new MyBeanPostProcessor();
    // }
}
