package com.ljs.learn.aop.xml.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

// 方法执行后输出log
public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("after return");
    }
}
