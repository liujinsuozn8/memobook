package com.ljs.learn.base.javaconfig.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 不使用@Bean注解在配置类中注册该类，
// 通过@Component和@ComponentScan在包中扫描该类并注册到Spring中
@Component
public class Student {
    @Value("StudentName")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
