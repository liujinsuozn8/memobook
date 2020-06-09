package com.ljs.learn.pattern.iterator.base.aggregate;

import com.ljs.learn.pattern.iterator.base.Department;
import com.ljs.learn.pattern.iterator.base.iter.InfoCollegeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfoCollege implements College {
    List<Department> ds;
    // 保存当前数组的对象个数
    int numOfDs = 0;

    public InfoCollege() {
        ds = new ArrayList<>();
    }

    @Override
    public Iterator createIterator() {
        return new InfoCollegeIterator(this.ds);
    }

    @Override
    public String getName() {
        return "Info College";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department d = new Department(name, desc);
        ds.add(d) ;
    }
}
