package com.ljs.learn.pattern.strategy.improve;

import com.ljs.learn.pattern.strategy.improve.behavior.fly.BadFlyBehavior;
import com.ljs.learn.pattern.strategy.improve.behavior.swim.NoSwimBehavior;
import com.ljs.learn.pattern.strategy.improve.duck.Duck;
import com.ljs.learn.pattern.strategy.improve.duck.ToyDuck;
import com.ljs.learn.pattern.strategy.improve.duck.WildDuck;
import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 1. 创建并使用对象
        Duck wildDuck = new WildDuck();
        wildDuck.display();
        wildDuck.fly();
        wildDuck.quack();
        wildDuck.swim();

        Duck toyDuck = new ToyDuck();
        toyDuck.display();
        toyDuck.fly();
        toyDuck.quack();
        toyDuck.swim();

        // 2. 创建对象，并修改某个策略对象
        Duck wildDuck02 = new WildDuck();
        wildDuck02.setFlyBehavior(new BadFlyBehavior());
        wildDuck02.setSwimBehavior(new NoSwimBehavior());
        wildDuck02.display();
        wildDuck02.fly();
        wildDuck02.quack();
        wildDuck02.swim();
    }
}
