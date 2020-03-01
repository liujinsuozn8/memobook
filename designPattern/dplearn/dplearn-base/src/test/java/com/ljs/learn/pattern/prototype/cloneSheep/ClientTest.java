package com.ljs.learn.pattern.prototype.cloneSheep;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建1只羊
        Sheep sheep = new Sheep("aa", 1, "aaaa");

        // 克隆9只
        Sheep clone1 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone4 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone5 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone6 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone7 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone8 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep clone9 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());

        System.out.println(sheep);
        System.out.println(clone1);
        System.out.println(clone2);
        System.out.println(clone3);
        System.out.println(clone4);
        System.out.println(clone5);
        System.out.println(clone6);
        System.out.println(clone7);
        System.out.println(clone8);
        System.out.println(clone9);
    }
}
