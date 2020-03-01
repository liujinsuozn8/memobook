package com.ljs.learn.pattern.factory.abstractFactory.factory;

import com.ljs.learn.pattern.factory.abstractFactory.pizza.Pizza;

// 抽象工厂
public interface AbsFactory {
    Pizza createPizza(String orderType);
}
