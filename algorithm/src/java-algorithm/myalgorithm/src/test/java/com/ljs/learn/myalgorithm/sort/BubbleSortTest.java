package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class BubbleSortTest {
    @Test
    public void testSort01(){
        int[] array = {5, 2, 4, 1, 3};
        BubbleSort.sort01(array);
        System.out.println(Arrays.toString(array));
        // 输出: [1, 2, 3, 4, 5]
    }

    @Test
    public void testSort02(){
        int[] array = {5, 2, 4, 1, 3};
        BubbleSort.sort02(array);
        // 输出: [1, 2, 3, 4, 5]
    }
}
