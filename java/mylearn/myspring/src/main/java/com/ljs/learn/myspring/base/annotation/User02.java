package com.ljs.learn.myspring.base.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//等价于<bean id="user" class=".....User"/>
@Component("myUser02")
@Scope("singleton") //配置作用域：单例模式
public class User02 {
    //相当于<property name="name" value="User02Name"/>
    @Value("User02Name")
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

    @Value("20")
    public void setAge(int age) {
        this.age = age;
    }
}
