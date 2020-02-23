package com.ljs.learn.threads.usage.type02;

import org.junit.Test;

public class UsageType02 {
    @Test
    public void test01() {
        // 3. 创建Mythread的实例化对象
        MyRunner myRunner = new MyRunner();

        // 4. 将Mythread作为参数传递到Thread类的含参构造器中，创建线程对象
        Thread thread = new Thread(myRunner);

        // 5. 调用start()，启动线程
        thread.start(); //子线程输出

        // 主线程输出
        for (int i = 0; i < 100; i++) {
            System.out.println("*******" + i + "*********");
        }
    }
}

// 1. 定义Runnable接口的实现类
class MyRunner implements Runnable{
    @Override
    public void run() {
        // 2. 实现接口中的run()
        // 打印100以内的偶数
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(i);
            }
        }
    }
}