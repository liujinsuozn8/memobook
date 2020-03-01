package com.ljs.learn.pattern.factory.factoryMethod.order;

import com.ljs.learn.pattern.factory.factoryMethod.pizza.ChinaSubPizza01;
import com.ljs.learn.pattern.factory.factoryMethod.pizza.ChinaSubPizza02;
import com.ljs.learn.pattern.factory.factoryMethod.pizza.Pizza;

public class ChinaOrderPizza extends OrderPizza {
    @Override
    public Pizza createPizza(String orderType) {
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new ChinaSubPizza01();
        }else if (orderType.equals("sub02")){
            p = new ChinaSubPizza02();
        }
        return p;
    }
}
