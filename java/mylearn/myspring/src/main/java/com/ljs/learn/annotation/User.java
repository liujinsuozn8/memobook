package com.ljs.learn.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//等价于<bean id="user" class=".....User"/>
@Component
// @Scope("singleton") //配置作用域：单例模式
@Scope("prototype") //配置作用域：单例模式
public class User {
    //相当于<property name="name" value="TestName"/>
    @Value("TestName")
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

    @Value("18")
    public void setAge(int age) {
        this.age = age;
    }
}
