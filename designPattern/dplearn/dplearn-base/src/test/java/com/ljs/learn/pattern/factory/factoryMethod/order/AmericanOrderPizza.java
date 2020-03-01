package com.ljs.learn.pattern.factory.factoryMethod.order;

import com.ljs.learn.pattern.factory.factoryMethod.pizza.AmericanSubPizza01;
import com.ljs.learn.pattern.factory.factoryMethod.pizza.AmericanSubPizza02;
import com.ljs.learn.pattern.factory.factoryMethod.pizza.Pizza;

public class AmericanOrderPizza extends OrderPizza {
    @Override
    public Pizza createPizza(String orderType) {
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new AmericanSubPizza01();
        }else if (orderType.equals("sub02")){
            p = new AmericanSubPizza02();
        }
        return p;
    }
}
