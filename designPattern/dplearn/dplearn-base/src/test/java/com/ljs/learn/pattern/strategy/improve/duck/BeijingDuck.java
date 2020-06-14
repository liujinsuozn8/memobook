package com.ljs.learn.pattern.strategy.improve.duck;

import com.ljs.learn.pattern.strategy.improve.behavior.fly.BadFlyBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.quack.CanQuackBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.swim.CanSwimBehavior;

public class BeijingDuck extends Duck {
    public BeijingDuck() {
        flyBehavior = new BadFlyBehavior();
        quackBehavior = new CanQuackBehavior();
        swimBehavior = new CanSwimBehavior();
    }

    @Override
    public void display() {
        System.out.println("BeijingDuck");
    }
}
