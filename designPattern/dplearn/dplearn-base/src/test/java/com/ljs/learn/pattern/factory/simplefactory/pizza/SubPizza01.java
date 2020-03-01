package com.ljs.learn.pattern.factory.simplefactory.pizza;

public class SubPizza01 extends Pizza {

    public SubPizza01() {
        super("SubPizza01");
    }

    @Override
    public void prepare() {
        System.out.println("SubPizza01 is preparing");
    }
}
