package com.ljs.learn.threads.synchronize;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    @Test
    public void test01(){
        Window5 w = new Window5();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.start();
        t2.start();
        t3.start();
    }
}

class Window5 implements Runnable{
    private int ticket = 100;
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try {
                // 线程加锁
                lock.lock();
                if (ticket > 0){
                    System.out.println(Thread.currentThread().getName() + ", ticket = " + ticket);
                    ticket--;
                }else{
                    break;
                }
            } finally {
                // 线程解锁
                lock.unlock();
            }
        }
    }
}