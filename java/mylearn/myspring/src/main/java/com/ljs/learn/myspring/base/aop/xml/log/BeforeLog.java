package com.ljs.learn.myspring.base.aop.xml.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

//方法执行前输出log
public class BeforeLog implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("before log");
    }
}
