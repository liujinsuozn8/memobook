package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSortTest {
    @Test
    public void test01(){
        int[] array = {5, 2, 1, 3, 7, 4, 6, 5};
        QuickSort.sort(array, 0, array.length-1);
        System.out.println(Arrays.toString(array));
        // 输出: [1, 2, 3, 4, 5, 5, 6, 7]
    }

    @Test
    public void testSortByRight(){
        int[] array = {5, 2, 1, 3, 7, 4, 6, 5};
        QuickSort.sortByRight(array, 0, array.length-1);
        System.out.println(Arrays.toString(array));
        // 输出: [1, 2, 3, 4, 5, 5, 6, 7]
    }
}
