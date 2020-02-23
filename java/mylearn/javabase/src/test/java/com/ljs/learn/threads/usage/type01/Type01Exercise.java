package com.ljs.learn.threads.usage.type01;

import org.junit.Test;


public class Type01Exercise {
    //同时分别遍历100以内的偶数和基数
    @Test
    public void test01(){
        A a = new A();
        B b = new B();

        a.start();
        b.start();
    }
}

//遍历偶数
class A extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println("A = " + i);
            }
        }
    }
}

//遍历奇数
class B extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%2!=0){
                System.out.println("B = " + i);
            }
        }
    }
}
