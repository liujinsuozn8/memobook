package com.ljs.learn.myspringannotation.autowired.demo02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss {
    private Car car;
    private Work work;

    // 4. 标记方法参数

    // 4.1 标记在构造器参数
    public Boss(@Autowired Car car) {
        this.car = car;
    }

    public Work getWork() {
        return work;
    }

    // 4.2 标记在 setter 的方法上，会失效
    public void setWork(@Autowired Work work) {
        this.work = work;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }
}
