package com.ljs.learn.pattern.factory.factoryMethod.pizza;

public class AmericanSubPizza02 extends Pizza {
    public AmericanSubPizza02() {
        super("AmericanSubPizza02");
    }

    @Override
    public void prepare() {
        System.out.println("AmericanSubPizza02 is preparing");
    }
}
