package com.ljs.learn.myspring.base.aop.annotation.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect  // 表示该类是一个切面
public class Log {
    //配置前置通知
    @Before("execution(* com.ljs.learn.myspring.base.aop.annotation.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("Log:before log");
    }

    //配置后置通知
    @After("execution(* com.ljs.learn.myspring.base.aop.annotation.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("Log:after log");
    }

    //配置环绕通知
    @Around("execution(* com.ljs.learn.myspring.base.aop.annotation.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint p) throws Throwable {
        System.out.println("around before");
        Object proceed = p.proceed();
        System.out.println("around after");
    }
}
