package com.ljs.learn.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {
    //维护一个目标对象
    private T target;

    public ProxyFactory(T target) {
        this.target = target;
    }

    //给目标对象生成一个代理对象
    public T getProxyInstance(){
        return (T)Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("proxy start");
                        // 通过反射调用目标对象的方法
                        Object invoke = method.invoke(target, args);

                        System.out.println("proxy end");
                        return invoke;
                    }
                }
        );
    }
}
