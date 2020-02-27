package com.ljs.learn.pattern.singleton.type3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SingletonType03Test {
    public static void main(String[] args) {
        Callable<Singleton> r = () -> {
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
        // - 创建步骤
        // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
        private Singleton() {
        }
        // 2. 添加一个**私有的、静态的**成员属性`instance`
        private static Singleton instance;

        // 3. 向外暴露一个静态的公共方法，一般写作：`getInstance()`
        public static Singleton getInstance(){
            // 4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
            if (instance == null){
                instance = new Singleton();
            }
            return instance;
    }
}
