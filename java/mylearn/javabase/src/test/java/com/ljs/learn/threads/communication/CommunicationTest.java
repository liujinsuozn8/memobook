package com.ljs.learn.threads.communication;

import org.junit.Test;

public class CommunicationTest {
    // 使用两个线程交替打印1-100
    @Test
    public void test01(){
        PrintNumbers p = new PrintNumbers();
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(p);

        t1.start();
        t2.start();
    }
}

class PrintNumbers implements Runnable{
    private int number=1;
    private Object obj = new Object();

    @Override
    public void run() {
        while(true){
            synchronized (obj) {
                // synchronized (this) {
                obj.notify(); // 通过同步监视器来调用
                if (number <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;

                    // 使当前线程进行阻塞状态
                    try {
                        obj.wait();// 通过同步监视器来调用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}