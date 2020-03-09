package com.ljs.learn.pattern.decorator.base;

public class Decorator extends Drink {
    private Drink obj;

    public Decorator(Drink obj) {
        this.obj = obj;
    }

    @Override
    public float cost() {
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDes() {
        return this.des +getPrice() + "&&" + obj.getDes();
    }
}
