package com.ljs.learn.pattern.strategy.improve.behavior.fly;

public class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("无法飞行");
    }
}
