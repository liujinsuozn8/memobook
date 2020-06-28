package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class SelectSortTest {
    @Test
    public void test01(){
        int[] array = {5, 3, 2, 1, 7, 4, 6, 5};
        SelectSort.sort(array);
        System.out.println(Arrays.toString(array));
        // 输出: [1, 2, 3, 4, 5, 5, 6, 7]
    }
}
