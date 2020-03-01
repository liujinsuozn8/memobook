package com.ljs.learn.pattern.factory.abstractFactory.pizza;

public class ChinaSubPizza02 extends Pizza {
    public ChinaSubPizza02() {
        super("ChinaSubPizza02");
    }

    @Override
    public void prepare() {
        System.out.println("ChinaSubPizza02 is preparing");
    }
}
