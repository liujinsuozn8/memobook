package com.ljs.learn.pattern.factory.simplefactory.factory;

import com.ljs.learn.pattern.factory.simplefactory.pizza.Pizza;
import com.ljs.learn.pattern.factory.simplefactory.pizza.SubPizza01;
import com.ljs.learn.pattern.factory.simplefactory.pizza.SubPizza02;

// 简单工厂模式
public class PizzaFactory {
    public Pizza create(String orderType){
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new SubPizza01();
        }else if (orderType.equals("sub02")){
            p = new SubPizza02();
        }
        return p;
    }
}
