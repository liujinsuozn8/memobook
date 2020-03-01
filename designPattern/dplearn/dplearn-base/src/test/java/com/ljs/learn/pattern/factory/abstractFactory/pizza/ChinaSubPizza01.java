package com.ljs.learn.pattern.factory.abstractFactory.pizza;

public class ChinaSubPizza01 extends Pizza {

    public ChinaSubPizza01() {
        super("ChinaSubPizza01");
    }

    @Override
    public void prepare() {
        System.out.println("ChinaSubPizza01 is preparing");
    }
}
