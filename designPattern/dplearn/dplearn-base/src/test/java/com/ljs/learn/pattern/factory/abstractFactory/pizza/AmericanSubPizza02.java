package com.ljs.learn.pattern.factory.abstractFactory.pizza;

public class AmericanSubPizza02 extends Pizza {
    public AmericanSubPizza02() {
        super("AmericanSubPizza02");
    }

    @Override
    public void prepare() {
        System.out.println("AmericanSubPizza02 is preparing");
    }
}
