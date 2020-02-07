package com.ljs.learn.collection.set;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetTest {
    // LinkedHashSet的迭代有序性测试
    @Test
    public void testForeach(){
        Set set = new LinkedHashSet();
        set.add("aa");
        set.add("aa");
        set.add(1234);
        set.add("bb");
        set.add(new Date());
        set.add("cc");

        for (Object o : set) {
            System.out.println(o);
        }
    }
}
