package com.ljs.learn.pattern.template.hook;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 制作红豆豆浆
        System.out.println("-----redBeanSoyaMilk-----");
        SoyaMilk redBeanSoyaMilk = new RedBeanSoyaMilk();
        redBeanSoyaMilk.make();

        // 制作纯豆浆
        System.out.println("-----pureSoyaMilk-----");
        SoyaMilk pureSoyaMilk = new PureSoyaMilk();
        pureSoyaMilk.make();
    }
}
