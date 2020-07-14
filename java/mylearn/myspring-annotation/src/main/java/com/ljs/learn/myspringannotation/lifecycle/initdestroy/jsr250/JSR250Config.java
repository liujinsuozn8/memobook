package com.ljs.learn.myspringannotation.lifecycle.initdestroy.jsr250;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = "com.ljs.learn.myspringannotation.lifecycle.initdestroy.jsr250")
public class JSR250Config {
    // 扫描Car的多实例bean，注册一个Car的单实例bean
    // 单实例
    @Bean
    public Car singleCar(){
        Car car = new Car();
        car.name = "singleCar";
        return car;
    }
}