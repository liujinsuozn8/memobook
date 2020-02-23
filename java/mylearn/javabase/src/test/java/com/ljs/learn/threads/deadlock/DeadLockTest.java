package com.ljs.learn.threads.deadlock;

public class DeadLockTest {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                synchronized (s1){
                    s1.append("a");
                    s2.append("1");

                    // 通过sleep来增加死锁的概率
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (s2){
                        s1.append("b");
                        s2.append("2");
                    }
                }

                System.out.println(s1);
                System.out.println(s2);
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                // 交换锁的顺序
                synchronized (s2){
                    s1.append("c");
                    s2.append("3");

                    // 通过sleep来增加死锁的概率
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (s1){
                        s1.append("d");
                        s2.append("4");
                    }
                }

                System.out.println(s1);
                System.out.println(s2);
            }
        }.start();
    }
}