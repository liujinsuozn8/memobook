package com.ljs.learn.threads.synchronize;

import org.junit.Test;

// 使用同步代码块+Runnable 处理3个窗口买票的问题
public class WindowsTest {
    @Test
    public void test01(){
        Window window = new Window();

        Thread t1 = new Thread(window);
        Thread t2 = new Thread(window);
        Thread t3 = new Thread(window);

        t1.start();
        t2.start();
        t3.start();
    }
}

class Window implements Runnable{
    private int ticket = 100;
    // Object obj = new Object();
    @Override
    public void run() {
        // synchronized(obj) {
        synchronized(this) {
            while (true) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ", ticker=" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}
