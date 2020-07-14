package com.ljs.learn.myspringannotation.lifecycle.initdestroy.interfaces;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class InterfacesConfig {
    // 只使用 InitializingBean接口、 DisposableBean接口
    @Bean
    public Car singleCar(){
        return new Car();
    }

    // 单实例
    // 同时使用 InitializingBean接口、 DisposableBean接口 和 bean的初始化和销毁的参数
    @Bean(initMethod = "init", destroyMethod = "end")
    public Computer singleComputer(){
        return new Computer();
    }

    // 多实例
    // 同时使用 InitializingBean接口、 DisposableBean接口 和 bean的初始化和销毁的参数
    @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "end")
    public Computer prototypeComputer(){
        return new Computer();
    }

}
