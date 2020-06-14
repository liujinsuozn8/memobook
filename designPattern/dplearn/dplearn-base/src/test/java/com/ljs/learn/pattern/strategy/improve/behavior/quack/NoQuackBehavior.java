package com.ljs.learn.pattern.strategy.improve.behavior.quack;

public class NoQuackBehavior implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("不能叫");
    }
}
