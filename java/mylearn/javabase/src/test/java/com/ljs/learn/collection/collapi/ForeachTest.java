package com.ljs.learn.collection.collapi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ForeachTest {
    @Test
    public void testForeachCollection(){
        Collection coll = new ArrayList();
        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        for (Object node : coll) {
            System.out.println(node);
        }
    }

    @Test
    public void testForeachArray(){
        int[] arrays = new int[]{33,55,2,6,4,6,8};
        for (int array : arrays) {
            System.out.println(array);
        }
    }

    @Test
    public void testForeachUpdateString(){
        String[] arr = new String[]{"aa","aa","aa"};

        //普通循环遍历，并修改元素值
        for (int i = 0; i < arr.length; i++) {
            arr[i] = "bbb";
        }
        //打印结果
        for (String s : arr) {
            System.out.println(s);
        }

        //使用foreach来遍历，并修改元素
        arr = new String[]{"aa","aa","aa"};
        for (String s : arr) {
            s = "ccc";
        }
        //打印结果
        for (String s : arr) {
            System.out.println(s);
        }
    }
}
