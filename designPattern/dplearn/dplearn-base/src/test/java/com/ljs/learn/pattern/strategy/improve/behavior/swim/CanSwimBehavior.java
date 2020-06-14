package com.ljs.learn.pattern.strategy.improve.behavior.swim;

public class CanSwimBehavior implements SwimBehavior {
    @Override
    public void swim() {
        System.out.println("可以游泳");
    }
}
