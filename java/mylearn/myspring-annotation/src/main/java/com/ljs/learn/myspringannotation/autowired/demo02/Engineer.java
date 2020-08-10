package com.ljs.learn.myspringannotation.autowired.demo02;

public class Engineer {
    private Car car;
    private Work work;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
