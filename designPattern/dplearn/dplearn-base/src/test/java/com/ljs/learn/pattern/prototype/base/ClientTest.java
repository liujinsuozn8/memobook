package com.ljs.learn.pattern.prototype.base;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建1只羊
        Sheep sheep = new Sheep("aa", 1, "aaaa");

        // 克隆9只
        Sheep clone1 = (Sheep) sheep.clone();

        System.out.println(sheep);
        System.out.println(clone1);

        // 修改原始对象的属性
        sheep.setAge(22);

        System.out.println(sheep);
        System.out.println(clone1);
    }
}
