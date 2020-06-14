package com.ljs.learn.pattern.strategy.improve.behavior.quack;

public class CanQuackBehavior implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("可以发出叫声");
    }
}
