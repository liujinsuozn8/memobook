package com.ljs.learn.pattern.bridge.base;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        Phone foldedPhone = new FoldedPhone(new XiaoMi());
        foldedPhone.open();
        foldedPhone.call();
        foldedPhone.close();

        System.out.println("-----------------");

        FoldedPhone foldedPhone2 = new FoldedPhone(new Vivo());
        foldedPhone2.open();
        foldedPhone2.call();
        foldedPhone2.close();

        System.out.println("-----------------");

        UprightPhone uprightPhone = new UprightPhone(new XiaoMi());
        uprightPhone.open();
        uprightPhone.call();
        uprightPhone.close();
    }
}
