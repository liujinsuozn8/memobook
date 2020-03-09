package com.ljs.learn.pattern.decorator.base;

public class Soy extends Decorator {
    public Soy(Drink obj) {
        super(obj);
        setDes("Soy");
        setPrice(3.0f);
    }
}
