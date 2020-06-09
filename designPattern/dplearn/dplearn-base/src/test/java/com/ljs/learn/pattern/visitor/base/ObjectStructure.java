package com.ljs.learn.pattern.visitor.base;

import java.util.LinkedList;
import java.util.List;

// 数据结构，管理person
public class ObjectStructure {
    private List<Person> elements = new LinkedList<>();

    // 添加
    public void attach(Person p){
        elements.add(p);
    }

    // 删除
    public void detach(Person p){
        elements.remove(p);
    }

    // 遍历
    public void display(Action action){
        for (Person p : elements) {
            p.accept(action);
        }
    }
}
