package com.ljs.learn.myspringannotation.lifecycle.initdestroy.bean;

public class Car {
    public Car() {
        System.out.println("car constructor");
    }

    // 初始化方法
    public void init (){
        System.out.println("car init");
    }

    // 销毁方法
    public void destroy(){
        System.out.println("car destroy");
    }
}
