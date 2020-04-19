package com.ljs.learn.myspring.base.aop.aspect.log;

public class Log {
    public void before(){
        System.out.println("Log:before log");
    }

    public void after(){
        System.out.println("Log:after log");
    }
}
