package com.ljs.learn.collection.map;

import org.junit.Test;

import java.util.Map;

public class LinkedHashMapTest {
    @Test
    public void testSequence(){
        Map map = new java.util.LinkedHashMap();
        map.put("ccc",111);
        map.put("aaa",222);
        map.put("bbb",333);

        System.out.println(map);
        //output:
        //{ccc=111, aaa=222, bbb=333}
    }
}
