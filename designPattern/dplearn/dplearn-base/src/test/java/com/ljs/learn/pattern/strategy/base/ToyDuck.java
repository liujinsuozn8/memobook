package com.ljs.learn.pattern.strategy.base;

public class ToyDuck extends Duck {
    @Override
    public void display() {
        System.out.println("ToyDuck");
    }
    public void quack(){
        System.out.println("玩具鸭不能叫");
    }
    public void swim(){
        System.out.println("玩具鸭不能游泳");
    }
    public void fly(){
        System.out.println("玩具鸭不能飞行");
    }
}
