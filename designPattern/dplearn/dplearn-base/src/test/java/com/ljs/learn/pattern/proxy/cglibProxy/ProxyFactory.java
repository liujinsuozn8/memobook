package com.ljs.learn.pattern.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory<T> implements MethodInterceptor {
    private T target;

    public ProxyFactory(T target) {
        this.target = target;
    }

    // 返回一个target的代理对象
    public T getProxyInstance(){
        // 1. 创建一个工具类
        Enhancer enhancer = new Enhancer();
        // 2. 设置父类
        enhancer.setSuperclass(target.getClass());
        // 3. 设置回调函数
        enhancer.setCallback(this);
        // 4. 创建子类，即代理对象
        return (T)enhancer.create();
    }

    // 重写intercept方法，实现对目标对象方法的调用
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("proxy is start");
        Object invoke = method.invoke(target, args);
        System.out.println("prroxy is end");
        return invoke;
    }
}
