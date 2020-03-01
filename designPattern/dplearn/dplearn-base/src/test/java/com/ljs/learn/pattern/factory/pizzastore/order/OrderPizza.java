package com.ljs.learn.pattern.factory.pizzastore.order;

import com.ljs.learn.pattern.factory.pizzastore.pizza.Pizza;
import com.ljs.learn.pattern.factory.pizzastore.pizza.SubPizza01;
import com.ljs.learn.pattern.factory.pizzastore.pizza.SubPizza02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
    public void order(){
        Pizza p = null;
        String orderType;

        do{
            orderType = getType();
            //创建pizza对象
            if (orderType.equals("sub01")){
                p = new SubPizza01();
            }else if (orderType.equals("sub02")){
                p = new SubPizza02();
            }else{
                break;
            }
            //制作pizza
            p.prepare();
            p.bake();
            p.cut();
            p.box();
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
