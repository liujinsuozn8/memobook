package com.ljs.learn.pattern.strategy.base;

public class BeijingDuck extends Duck {
    @Override
    public void display() {
        System.out.println("BeijingDuck");
    }

    @Override
    public void fly() {
        System.out.println("北京鸭不能飞行");
    }
}
