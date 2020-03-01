package com.ljs.learn.pattern.factory.abstractFactory.factory;

import com.ljs.learn.pattern.factory.abstractFactory.pizza.*;

// 抽象工厂的实现类
public class AmericanFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("use AmericanFactory");
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new AmericanSubPizza01();
        }else if (orderType.equals("sub02")){
            p = new AmericanSubPizza02();
        }
        return p;
    }
}
