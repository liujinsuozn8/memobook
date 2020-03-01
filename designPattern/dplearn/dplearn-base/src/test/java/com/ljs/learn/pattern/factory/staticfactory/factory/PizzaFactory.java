package com.ljs.learn.pattern.factory.staticfactory.factory;

import com.ljs.learn.pattern.factory.staticfactory.pizza.Pizza;
import com.ljs.learn.pattern.factory.staticfactory.pizza.SubPizza01;
import com.ljs.learn.pattern.factory.staticfactory.pizza.SubPizza02;

// 静态工厂模式
public class PizzaFactory {
    // 通过静态方法对外提供创建方式
    public static Pizza create(String orderType){
        Pizza p = null;

        if (orderType.equals("sub01")){
            p = new SubPizza01();
        }else if (orderType.equals("sub02")){
            p = new SubPizza02();
        }
        return p;
    }
}