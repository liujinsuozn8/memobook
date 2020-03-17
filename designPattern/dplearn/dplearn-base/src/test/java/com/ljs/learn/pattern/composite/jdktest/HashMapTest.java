package com.ljs.learn.pattern.composite.jdktest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    @Test
    public void test01(){
        Map<String, String> map = new HashMap<>();
        map.put("01", "aaa");
        map.put("02", "bbb");

        Map<String, String> subMap = new HashMap<>();
        subMap.put("sub01", "xxx");
        subMap.put("sub02", "yyy");

        map.putAll(subMap);
    }
}
