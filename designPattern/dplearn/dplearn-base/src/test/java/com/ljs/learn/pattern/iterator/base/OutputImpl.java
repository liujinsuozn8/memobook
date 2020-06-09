package com.ljs.learn.pattern.iterator.base;

import com.ljs.learn.pattern.iterator.base.aggregate.College;

import java.util.Iterator;
import java.util.List;

public class OutputImpl {
    // 学院的集合
    List<College> cs;

    public OutputImpl(List<College> cs) {
        this.cs = cs;
    }

    // 遍历所有学院，然后调用printDepartment遍历学院下的系
    public void printCollege(){
        // 取出所有学院
        // java中的List已经实现了Iterator
        Iterator<College> iterator = cs.iterator();
        while (iterator.hasNext()){
            // 取出一个学院
            College c = iterator.next();
            System.out.println(c.getName());
            // 得到对应的迭代器
            printDepartment(c.createIterator());
        }
    }

    // 输出系
    public void printDepartment(Iterator iter){
        while (iter.hasNext()){
            Department d =(Department) iter.next();
            System.out.println(d.getName());
        }
    }
}
