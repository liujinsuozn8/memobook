package com.ljs.learn.myspringannotation.autowired.demo02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {

    // 1. 添加在 属性 上
    @Autowired
    private House house;

    private Car car;

    private Work work;

    // 3. 添加在有参构造器
    @Autowired
    public Person(Work work) {
        this.work = work;
        System.out.println("Person 有参构造");
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Car getCar() {
        return car;
    }

    // 2. 添加在setter方法上
    @Autowired
    public void setCar(Car car) {
        System.out.println("call setCar");
        this.car = car;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
