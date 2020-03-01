package com.ljs.learn.pattern.factory.abstractFactory.store;

import com.ljs.learn.pattern.factory.abstractFactory.factory.AbsFactory;
import com.ljs.learn.pattern.factory.abstractFactory.factory.ChinaFactory;
import com.ljs.learn.pattern.factory.abstractFactory.order.OrderPizza;

public class PizzaStore {
    public static void main(String[] args) {
        //创建抽象工厂并注入订单类中
        AbsFactory f = new ChinaFactory();
        OrderPizza orderPizza = new OrderPizza(f);
        orderPizza.order();
    }
}
