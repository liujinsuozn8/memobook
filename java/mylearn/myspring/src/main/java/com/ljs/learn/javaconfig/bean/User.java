package com.ljs.learn.javaconfig.bean;

import org.springframework.beans.factory.annotation.Value;

// @Component 不使用该注解，直接在MyConfig配置类中使用@Bean进行注册
public class User {
    @Value("ConfigUserName")
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
