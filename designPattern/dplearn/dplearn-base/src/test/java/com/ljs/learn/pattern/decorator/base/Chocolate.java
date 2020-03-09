package com.ljs.learn.pattern.decorator.base;

public class Chocolate extends Decorator {
    public Chocolate(Drink obj) {
        super(obj);
        setDes("Chocolate");
        setPrice(5.0f);
    }
}
