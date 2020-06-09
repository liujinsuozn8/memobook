package com.ljs.learn.pattern.iterator.jdkAnalyze;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListTest {

    @Test
    public void tet01(){
        // 1. 获得一个聚合器接口实例
        List<String> a = new ArrayList<>();
        a.add("aaaa");
        a.add("bbbb");
        a.add("cccc");

        // 2. 创建迭代器
        Iterator<String> iter = a.iterator();

        // 3. 开始迭代
        while( iter.hasNext() ){
            System.out.println(iter.next());
        }
    }
}
