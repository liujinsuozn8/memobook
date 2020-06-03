package com.ljs.learn.pattern.template.base;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 制作红豆豆浆
        System.out.println("-----redBeanSoyaMilk-----");
        SoyaMilk redBeanSoyaMilk = new RedBeanSoyaMilk();
        redBeanSoyaMilk.make();

        // 制作花生豆浆
        System.out.println("-----peanutSoyaMilk-----");
        SoyaMilk peanutSoyaMilk = new PeanutSoyaMilk();
        peanutSoyaMilk.make();
    }
}
