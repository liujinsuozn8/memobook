package com.ljs.learn.pattern.strategy.base;

public abstract class Duck {
    // 显示鸭子的信息
    public abstract void display();

    public void quack(){
        System.out.println("鸭子叫");
    }
    public void swim(){
        System.out.println("鸭子游泳");
    }
    public void fly(){
        System.out.println("鸭子飞行");
    }
}
