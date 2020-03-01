package com.ljs.learn.pattern.factory.factoryMethod.pizza;

public class AmericanSubPizza01 extends Pizza {

    public AmericanSubPizza01() {
        super("AmericanSubPizza01");
    }

    @Override
    public void prepare() {
        System.out.println("AmericanSubPizza01 is preparing");
    }
}
