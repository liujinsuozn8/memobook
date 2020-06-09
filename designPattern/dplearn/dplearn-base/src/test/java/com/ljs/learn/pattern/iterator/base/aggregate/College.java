package com.ljs.learn.pattern.iterator.base.aggregate;

import java.util.Iterator;

public interface College {
    Iterator createIterator();

    // 返回当前学院的名称
    String getName();

    // 增加系的方法
    void addDepartment(String name, String desc);
}
