package com.ljs.learn.pattern.decorator.base;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        Drink coffee = new LongBlack();

        //添加1份牛奶
        coffee = new Milk(coffee);
        //添加2份巧克力
        coffee = new Chocolate(new Chocolate(coffee));

        System.out.println(coffee.cost());
        System.out.println(coffee.getDes());
    }
}
