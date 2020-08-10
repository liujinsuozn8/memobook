package com.ljs.learn.myspringannotation.autowired.demo02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Teacher {
    private Work work;
    private Car car;

    // 如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略
    public Teacher(Work work, Car car) {
        this.work = work;
        this.car = car;
    }

    public Work getWork() {
        return work;
    }

    public void setWork( Work work) {
        this.work = work;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
