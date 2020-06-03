package com.ljs.learn.pattern.flyweight.jdk;

import org.junit.Test;

public class IntegerTest {
    @Test
    public void test01(){
        Integer x = Integer.valueOf(127);
        Integer y = new Integer(127);
        Integer z = Integer.valueOf(127);
        Integer w = new Integer(127);
        System.out.println(x.equals(y));    // true
        System.out.println(x == y);         // false
        // [-128,127]范围内使用valueOf创建的实例使用享元模式
        System.out.println(x == z);         // true
        System.out.println(w == x);         // false
        System.out.println(w == y);         // false

        // [-128,127]范围外，使用new创建新的实例并返回
        Integer x1 = Integer.valueOf(200);
        Integer x2= Integer.valueOf(200);
        System.out.println(x1 == x2);       // false, 在 -127～128的范围外
    }
}
