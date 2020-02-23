package com.ljs.learn.threads.synchronize;

import org.junit.Test;

// 使用同步代码块 + Thread 处理3个窗口买票的问题
public class WindowsTest2 {
    @Test
    public void test01(){
        Window2 w1 = new Window2();
        Window2 w2 = new Window2();
        Window2 w3 = new Window2();

        w1.start();
        w2.start();
        w3.start();
    }
}

class Window2 extends Thread{
    private static int ticket = 100;
    @Override
    public void run() {
        synchronized(Window2.class) {
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
