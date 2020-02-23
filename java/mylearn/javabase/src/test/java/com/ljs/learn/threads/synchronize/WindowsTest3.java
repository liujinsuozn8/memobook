package com.ljs.learn.threads.synchronize;

import org.junit.Test;

// 使用同步方法 + Runnable 处理3个窗口买票的问题
public class WindowsTest3 {
    @Test
    public void test01(){
        Window3 window = new Window3();

        Thread t1 = new Thread(window);
        Thread t2 = new Thread(window);
        Thread t3 = new Thread(window);

        t1.start();
        t2.start();
        t3.start();
    }
}

class Window3 implements Runnable{
    private int ticket = 100;
    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    public synchronized void show(){
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ", ticker=" + ticket);
            ticket--;
        }
    }
}
