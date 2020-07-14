package com.ljs.learn.myspringannotation.lifecycle.initdestroy.jsr250;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Scope("prototype") // 多实例
@Component("prototypeCar")
public class Car {
    String name = "prototypeCar";

    @PostConstruct
    public void init(){
        System.out.println("car init, name=" + name);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("car destroy, name=" + name);
    }
}
