package com.ljs.learn.pattern.strategy.improve.duck;

import com.ljs.learn.pattern.strategy.improve.behavior.fly.GoodFlyBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.quack.CanQuackBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.swim.CanSwimBehavior;

public class WildDuck extends Duck {
    public WildDuck() {
        flyBehavior = new GoodFlyBehavior();
        quackBehavior = new CanQuackBehavior();
        swimBehavior = new CanSwimBehavior();
    }

    @Override
    public void display() {
        System.out.println("WildDuck");
    }
}
