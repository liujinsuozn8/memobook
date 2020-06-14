package com.ljs.learn.pattern.strategy.improve.behavior.swim;

public class NoSwimBehavior implements SwimBehavior {
    @Override
    public void swim() {
        System.out.println("无法游泳");
    }
}
