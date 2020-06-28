package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class ShellSortTest {
    // 分步实现测试
    @Test
    public void testSortStep(){
        int[] array = {8,9,1,7,2,3,5,4,6,0};
        ShellSort.sortStep(array);
        // 输出
        // 第1轮: [3, 5, 1, 6, 0, 8, 9, 4, 7, 2]
        // 第2轮: [0, 2, 1, 4, 3, 5, 7, 6, 9, 8]
        // 第3轮: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // 交换式希尔排序测试
    @Test
    public void testSortBySwap(){
        int[] array = {8,9,1,7,2,3,5,4,6,0};
        ShellSort.sortBySwap(array);
        System.out.println(Arrays.toString(array));
        // 输出: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // 移动式希尔排序测试
    @Test
    public void testSortByMove(){
        int[] array = {8,9,1,7,2,3,5,4,6,0};
        ShellSort.sortByMove(array);
        System.out.println(Arrays.toString(array));
        // 输出: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
