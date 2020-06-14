package com.ljs.learn.pattern.strategy.improve.duck;

import com.ljs.learn.pattern.strategy.improve.behavior.fly.NoFlyBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.quack.NoQuackBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.swim.NoSwimBehavior;

public class ToyDuck extends Duck {
    public ToyDuck() {
        flyBehavior = new NoFlyBehavior();
        quackBehavior = new NoQuackBehavior();
        swimBehavior = new NoSwimBehavior();
    }

    @Override
    public void display() {
        System.out.println("ToyDuck");
    }
}
