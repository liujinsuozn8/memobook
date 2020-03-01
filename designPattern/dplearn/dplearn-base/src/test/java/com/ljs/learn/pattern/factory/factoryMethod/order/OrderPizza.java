package com.ljs.learn.pattern.factory.factoryMethod.order;

import com.ljs.learn.pattern.factory.factoryMethod.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class OrderPizza {
    // 工厂方法模式，将实现交给子类
    public abstract Pizza createPizza(String orderType);

    public void order(){
        Pizza p = null;
        String orderType;

        do{
            orderType = getType();
            // 使用静态工厂创建pizza
            p = createPizza(orderType);
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
