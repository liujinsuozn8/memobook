package com.ljs.learn.pattern.iterator.base.iter;

import com.ljs.learn.pattern.iterator.base.Department;

import java.util.Iterator;

public class ComputerCollegeIterator implements Iterator {
    // 需要知道Department是以怎样过的方式保存的
    Department[] ds;
    int position=0; //遍历的位置

    public ComputerCollegeIterator(Department[] ds) {
        this.ds = ds;
    }

    // 判断是否还有下一个元素
    @Override
    public boolean hasNext() {
        if (position >= ds.length || ds[position] == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        Department d = ds[position];
        position ++;
        return d;
    }

    // 删除方法，默认空实现
    @Override
    public void remove() {

    }
}
