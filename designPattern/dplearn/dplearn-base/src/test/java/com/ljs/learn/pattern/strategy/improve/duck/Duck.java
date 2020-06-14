package com.ljs.learn.pattern.strategy.improve.duck;

import com.ljs.learn.pattern.strategy.improve.behavior.fly.FlyBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.quack.QuackBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.swim.SwimBehavior;

public abstract class Duck {
    // 策略接口
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    SwimBehavior swimBehavior;

    // 显示鸭子的信息
    public abstract void display();

    // 调用策略对象实现功能
    public void quack(){
        if (quackBehavior != null){
            quackBehavior.quack();
        }
    }
    public void swim(){
        if (swimBehavior != null){
            swimBehavior.swim();
        }
    }
    public void fly(){
        if (flyBehavior != null){
            flyBehavior.fly();
        }
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void setSwimBehavior(SwimBehavior swimBehavior) {
        this.swimBehavior = swimBehavior;
    }
}
