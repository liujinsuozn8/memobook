package com.ljs.learn.pattern.factory.factoryMethod.store;


import com.ljs.learn.pattern.factory.factoryMethod.order.ChinaOrderPizza;
import com.ljs.learn.pattern.factory.factoryMethod.order.OrderPizza;

public class PizzaStore {
    public static void main(String[] args) {
        //作为客户端发出pizza的定做任务
        OrderPizza order = new ChinaOrderPizza();
        order.order();
    }
}
