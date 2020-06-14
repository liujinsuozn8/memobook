package com.ljs.learn.pattern.strategy.jdk;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class ArraysTest {
    @Test
    public void test01(){
        Integer[] data = {3,2,6,4,8,2,4};
        // 创建匿名类对象，按照降序排序
        // 相当于一个策略接口的对象
        // 在compare中执行策略
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        // [3, 2, 6, 4, 8, 2, 4]
        System.out.println(Arrays.toString(data));
        Arrays.sort(data, comparator);
        // [8, 6, 4, 4, 3, 2, 2]
        System.out.println(Arrays.toString(data));
    }
}
