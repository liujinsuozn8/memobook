package com.ljs.learn.threads.synchronize;

import org.junit.Test;

// 使用同步代码块 + Thread 处理3个窗口买票的问题
public class WindowsTest4 {
    @Test
    public void test01(){
        Window4 w1 = new Window4();
        Window4 w2 = new Window4();
        Window4 w3 = new Window4();

        w1.start();
        w2.start();
        w3.start();
    }
}

class Window4 extends Thread{
    private static int ticket = 100;
    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    public static synchronized void show(){
    // public synchronized void show(){ // 同步监视器是this，仍然会有线程安全问题
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ", ticker=" + ticket);
            ticket--;
        }
    }
}
