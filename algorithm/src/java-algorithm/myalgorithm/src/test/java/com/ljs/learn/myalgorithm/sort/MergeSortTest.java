package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class MergeSortTest {
    // 合并阶段测试
    @Test
    public void testMerge(){
        int[] array = {4,8,  5,7  ,1,3,  2,6};

        int[] temp = new int[array.length];
        //合并 0 ～ 3
        MergeSort.merge(array, 0, 1, 3, temp);
        System.out.println("合并 0 ～ 3: " + Arrays.toString(array));
        //合并 4 ～ 7
        MergeSort.merge(array, 4, 5, 7, temp);
        System.out.println("合并 4 ～ 7: " + Arrays.toString(array));
        //合并 0 ～ 7
        MergeSort.merge(array, 0, 3, 7, temp);
        System.out.println("合并 0 ～ 7: " + Arrays.toString(array));

        // 输出
        // 合并 0 ～ 3: [4, 5, 7, 8, 1, 3, 2, 6]
        // 合并 4 ～ 7: [4, 5, 7, 8, 1, 2, 3, 6]
        // 合并 0 ～ 7: [1, 2, 3, 4, 5, 6, 7, 8]
    }

    // 排序测试
    @Test
    public void testSort(){
        int[] array = {8,4,5,7,1,3,6,2};
        MergeSort.sort(array);
        System.out.println(Arrays.toString(array));
        // 输出
        // [1, 2, 3, 4, 5, 6, 7, 8]
    }
}
