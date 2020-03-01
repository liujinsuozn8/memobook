package com.ljs.learn.pattern.factory.abstractFactory.order;

import com.ljs.learn.pattern.factory.abstractFactory.factory.AbsFactory;
import com.ljs.learn.pattern.factory.abstractFactory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
    // 聚合一个抽象工厂子类
    private AbsFactory factory;

    public OrderPizza(AbsFactory factory) {
        this.factory = factory;
    }

    public void order(){
        Pizza p = null;
        String orderType;

        do{
            orderType = getType();
            // 使用抽象工厂的实现类创建pizza
            p = factory.createPizza(orderType);
            // 制作pizza
            if (p != null){
                p.prepare();
                p.bake();
                p.cut();
                p.box();
            }else {
                break;
            }
        }while(true);
    }

    // 从控制台获取pizza种类
    private String getType(){
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type : ");
            String type= is.readLine();
            return type;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
