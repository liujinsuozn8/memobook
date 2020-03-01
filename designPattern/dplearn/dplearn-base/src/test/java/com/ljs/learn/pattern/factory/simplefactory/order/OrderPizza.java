package com.ljs.learn.pattern.factory.simplefactory.order;

import com.ljs.learn.pattern.factory.simplefactory.factory.PizzaFactory;
import com.ljs.learn.pattern.factory.simplefactory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
    private PizzaFactory pf;
    public OrderPizza(PizzaFactory pf) {
        this.pf = pf;
    }

    public void order(){
        Pizza p = null;
        String orderType;

        do{
            orderType = getType();
            // 使用工厂创建pizza
            p = pf.create(orderType);
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
