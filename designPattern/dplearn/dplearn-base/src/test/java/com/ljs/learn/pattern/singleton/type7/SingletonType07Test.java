package com.ljs.learn.partten.singleton.type7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SingletonType07Test {
    public static void main(String[] args) {
        Callable<Singleton> r = ()-> {
            Singleton instance = Singleton.getInstance();
            System.out.println(
                    Thread.currentThread().getName() +
                            " : instance hashCode = " +
                            instance
            );
            return instance;
        };

        FutureTask<Singleton> f1 = new FutureTask<>(r);
        FutureTask<Singleton> f2 = new FutureTask<>(r);
        FutureTask<Singleton> f3 = new FutureTask<>(r);
        FutureTask<Singleton> f4 = new FutureTask<>(r);

        // 创建线程对象
        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);
        Thread t3 = new Thread(f3);
        Thread t4 = new Thread(f4);

        // 启动线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // 获取线程的返回单例对象并比较
        try {
            Singleton singleton1 = f1.get();
            Singleton singleton2 = f2.get();
            Singleton singleton3 = f3.get();
            Singleton singleton4 = f4.get();
            System.out.println(singleton1 == singleton2);
            System.out.println(singleton1 == singleton3);
            System.out.println(singleton1 == singleton4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Singleton{
    private Singleton() {
    }

    // 创建静态内部类及其静态属性
    private static class SingletonInstance{
        public static final Singleton INSTANCE = new Singleton();
    }

    // 直接返回静态内部类中的静态属性
    public static Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
