package com.ljs.learn.annotation.customize;

import java.util.List;


public class TestClass <@Myannotation T> {// 写在类型变量的声明语句中

    // 写在类型变量的声明语句中
    public <@Myannotation E> void show(List<E> list) throws  RuntimeException {
        for (E e : list) {
            System.out.println(e);
        }
    }
}

