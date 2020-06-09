package com.ljs.learn.pattern.iterator.base.aggregate;

import com.ljs.learn.pattern.iterator.base.Department;
import com.ljs.learn.pattern.iterator.base.iter.ComputerCollegeIterator;

import java.util.Iterator;

public class ComputerCollege implements College {
    Department[] ds;
    // 保存当前数组的对象个数
    int numOfDs = 0;

    public ComputerCollege() {
        ds = new Department[5];
    }

    @Override
    public Iterator createIterator() {
        return new ComputerCollegeIterator(this.ds);
    }

    @Override
    public String getName() {
        return "Computer College";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department d = new Department(name, desc);
        ds[numOfDs] = d;
        numOfDs++;
    }
}
