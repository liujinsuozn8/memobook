package com.ljs.learn.pattern.iterator.base.iter;

import com.ljs.learn.pattern.iterator.base.Department;

import java.util.Iterator;
import java.util.List;

public class InfoCollegeIterator implements Iterator {
    // 需要知道Department是以怎样过的方式保存的
    List<Department> ds;
    int position=-1; //遍历的位置

    public InfoCollegeIterator(List<Department> ds) {
        this.ds = ds;
    }

    // 判断是否还有下一个元素
    @Override
    public boolean hasNext() {
        if (position >= ds.size() - 1){
            return false;
        } else {
            position++;
            return true ;
        }
    }

    @Override
    public Object next() {
        Department d = ds.get(position);
        return d;
    }

    // 删除方法，默认空实现
    @Override
    public void remove() {

    }
}
