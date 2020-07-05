package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class RadixSortTest {
    // 分步求解测试
    @Test
    public void testSortStep(){
        int[] array = {246, 57, 83, 4, 345, 78};
        RadixSort.sortStep(array);

        // 输出：
        // 第1轮，个位: [83, 4, 345, 246, 57, 78]
        // 第2轮，十位: [4, 345, 246, 57, 78, 83]
        // 第3轮，百位: [4, 57, 78, 83, 246, 345]
    }

    // 排序测试
    @Test
    public void testSort(){
        int[] array = {246, 57, 83, 4, 345, 78};
        RadixSort.sort(array);
        System.out.println(Arrays.toString(array));
        // 输出: [4, 57, 78, 83, 246, 345]
    }

}
