package com.ljs.learn.pattern.factory.simplefactory.store;


import com.ljs.learn.pattern.factory.simplefactory.factory.PizzaFactory;
import com.ljs.learn.pattern.factory.simplefactory.order.OrderPizza;

public class PizzaStore {
    public static void main(String[] args) {
        //作为客户端发出pizza的定做任务
        OrderPizza order = new OrderPizza(new PizzaFactory());
        order.order();
    }
}
