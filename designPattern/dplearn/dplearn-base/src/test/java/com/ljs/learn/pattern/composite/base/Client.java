package com.ljs.learn.pattern.composite.base;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 创建大学
        University university01 = new University("University01");

        // 创建学院
        College college01 = new College("College01");
        College college02 = new College("College02");

        // 创建系
        Department department01 = new Department("Department01");
        Department department02 = new Department("Department02");

        Department department03 = new Department("Department03");
        Department department04 = new Department("Department04");

        // 将三中对象组合成树形结构
        college01.add(department01);
        college01.add(department02);

        college02.add(department03);
        college02.add(department04);

        university01.add(college01);
        university01.add(college02);

        // 从学校级别开始输出
        university01.print();
        System.out.println("##########################");

        // 从学院级别开始输出
        college02.print();

        // 输出结果
        // -----University:University01------
        // ---College:College01---
        // Department01
        // Department02
        // ---College:College02---
        // Department03
        // Department04
        // ###################
        // ---College:College02---
        // Department03
        // Department04
        // ###################
    }
}
