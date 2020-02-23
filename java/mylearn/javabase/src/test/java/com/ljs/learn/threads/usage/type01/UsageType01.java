package com.ljs.learn.threads.usage.type01;

import org.junit.Test;

public class UsageType01 {
    // 调用start()来启动线程
    @Test
    public void test01() {
        // 3. 创建Thread类的子类的对象，（在主线程中创建）
        MyThread myThread = new MyThread();
        // 4. 调用对象的`start()`，（创建新线程，内部自动调用`run()`）
        myThread.start(); //子线程输出

        // 主线程输出
        for (int i = 0; i < 100; i++) {
            System.out.println("*******" + i + "*********");
        }
    }

    // 直接调用run()来启动
    @Test
    public void test02() {
        // 3. 创建Thread类的子类的对象，（在主线程中创建）
        MyThread myThread = new MyThread();
        // 4. 调用对象的`start()`，（创建新线程，内部自动调用`run()`）
        myThread.run(); //通过run启动

        // 主线程输出
        for (int i = 0; i < 100; i++) {
            System.out.println("*******" + i + "*********");
        }
    }

    // 使用匿名子类来创建多线程
    @Test
    public void test03(){
        // 子线程输出
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                }
            }
        }.start();

        // 主线程输出
        for (int i = 0; i < 100; i++) {
            System.out.println("*******" + i + "*********");
        }
    }
}

// 1. 创建一个继承于Thread类的子类
class MyThread extends Thread{
    @Override
    public void run() {
        // 2. 重写Thread类的`run()`（在新线程中操作）
        // 打印100以内的偶数
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(i);
            }
        }
    }
}