package com.ljs.learn.datastructure.hashtable.demo1;

import org.junit.Test;

public class EmpHashTableTest {
    // 添加测试
    @Test
    public void testAdd(){
        EmpHashTable empHashTable = new EmpHashTable(5);
        empHashTable.add(new Employee(1,"aaa"));
        empHashTable.add(new Employee(7,"erth"));
        empHashTable.add(new Employee(2,"sdf"));
        empHashTable.add(new Employee(3,"bbb"));
        empHashTable.add(new Employee(6,"ccc"));

        empHashTable.list();
        // 输出
        // Employee{name=aaa, id=1}
        // Employee{name=ccc, id=6}
        // Employee{name=erth, id=7}
        // Employee{name=sdf, id=2}
        // Employee{name=bbb, id=3}
    }

    // 查询测试
    @Test
    public void testGet(){
        EmpHashTable empHashTable = new EmpHashTable(5);
        empHashTable.add(new Employee(1,"aaa"));
        empHashTable.add(new Employee(7,"erth"));
        empHashTable.add(new Employee(2,"sdf"));
        empHashTable.add(new Employee(3,"bbb"));
        empHashTable.add(new Employee(6,"ccc"));

        assert empHashTable.get(2).equals( new Employee(2,"sdf") );

        assert empHashTable.get(4) == null;
    }
}
