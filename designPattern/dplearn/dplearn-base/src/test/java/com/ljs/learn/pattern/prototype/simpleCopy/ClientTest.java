package com.ljs.learn.pattern.prototype.copyType;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 测试属性是对象时的拷贝结果

        // 创建1只羊
        Sheep sheep = new Sheep("aa", 1, "aaaa");
        sheep.friend = new Sheep("bbb", 2, "bbb");

        // 克隆9只
        Sheep clone1 = (Sheep) sheep.clone();
        Sheep clone2 = (Sheep) sheep.clone();
        Sheep clone3 = (Sheep) sheep.clone();
        Sheep clone4 = (Sheep) sheep.clone();
        Sheep clone5 = (Sheep) sheep.clone();
        Sheep clone6 = (Sheep) sheep.clone();
        Sheep clone7 = (Sheep) sheep.clone();
        Sheep clone8 = (Sheep) sheep.clone();
        Sheep clone9 = (Sheep) sheep.clone();

        System.out.println(sheep + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone1 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone2 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone3 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone4 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone5 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone6 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone7 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone8 + "，friend=" + sheep.friend.hashCode());
        System.out.println(clone9 + "，friend=" + sheep.friend.hashCode());
    }
}
