package com.ljs.learn.pattern.factory.abstractFactory.factory;

import com.ljs.learn.pattern.factory.abstractFactory.pizza.ChinaSubPizza01;
import com.ljs.learn.pattern.factory.abstractFactory.pizza.ChinaSubPizza02;
import com.ljs.learn.pattern.factory.abstractFactory.pizza.Pizza;

// 抽象工厂的实现类
public class ChinaFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("use ChinaFactory");
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new ChinaSubPizza01();
        }else if (orderType.equals("sub02")){
            p = new ChinaSubPizza02();
        }
        return p;
    }
}
