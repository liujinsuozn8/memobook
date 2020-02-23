package com.ljs.learn.threads.usage.type02;

import org.junit.Test;

public class Type02Exercise {
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
    @Override
    public void run() {
        while (true){
            if (ticket > 0){
                System.out.println(Thread.currentThread().getName() + ", ticker=" + ticket);
                ticket--;
            }else {
                break;
            }
        }
    }
}
