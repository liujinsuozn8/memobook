package com.ljs.learn.pattern.decorator.base;

public class Milk extends Decorator {
    public Milk(Drink obj) {
        super(obj);
        setDes("Milk");
        setPrice(2.0f);
    }
}
