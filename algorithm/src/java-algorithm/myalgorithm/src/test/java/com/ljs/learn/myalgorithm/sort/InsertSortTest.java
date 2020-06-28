package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class InsertSortTest {
    @Test
    public void testSortStep(){
        int[] array = {5, 3, 2, 1, 7, 4, 6, 5};
        InsertSort.sortStep(array);
        // 输出
        // 第1轮[3, 5, 2, 1, 7, 4, 6, 5]
        // 第2轮[2, 3, 5, 1, 7, 4, 6, 5]
        // 第3轮[1, 2, 3, 5, 7, 4, 6, 5]
    }

    @Test
    public void testSort(){
        int[] array = {5, 3, 2, 1, 7, 4, 6, 5};
        InsertSort.sort(array);
        System.out.println(Arrays.toString(array));
        // 输出
        // [1, 2, 3, 4, 5, 5, 6, 7]
    }
}
