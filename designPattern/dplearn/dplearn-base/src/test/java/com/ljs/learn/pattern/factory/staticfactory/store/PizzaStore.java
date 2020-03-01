package com.ljs.learn.pattern.factory.staticfactory.store;


import com.ljs.learn.pattern.factory.staticfactory.order.OrderPizza;

public class PizzaStore {
    public static void main(String[] args) {
        //作为客户端发出pizza的定做任务
        OrderPizza order = new OrderPizza();
        order.order();
    }
}
