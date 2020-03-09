package com.ljs.learn.pattern.decorator.base;

public class Coffee extends Drink {
    // 金额默认是父类的单价
    @Override
    public float cost() {
        return super.getPrice();
    }
}
