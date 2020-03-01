package com.ljs.learn.pattern.factory.staticfactory.pizza;

public class SubPizza02 extends Pizza {
    public SubPizza02() {
        super("SubPizza02");
    }

    @Override
    public void prepare() {
        System.out.println("SubPizza02 is preparing");
    }
}
