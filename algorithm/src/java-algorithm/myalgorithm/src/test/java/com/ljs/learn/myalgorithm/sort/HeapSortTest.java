package com.ljs.learn.myalgorithm.sort;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HeapSortTest {

    // 测试子树部分的大顶堆构建
    @Test
    public void adjustHeapForSubTree() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        // 计算最后一个非叶子结点的位置
        int lastNoLeafNodeIdx = array.length / 2 - 1;
        HeapSort.adjustHeapForSubTree(array, lastNoLeafNodeIdx, array.length);

        int[] result = {1, 2, 3, 4, 11, 6, 7, 8, 9, 10, 5};

        assertArrayEquals(array, result);
    }

    @Test
    public void adjustHeap(){
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        HeapSort.adjustHeap(array, array.length);

        int[] result = {11, 10,7,9,5,6,3,8,4,2,1};

        assertArrayEquals(array, result);
    }

    @Test
    public void sort() {
        int[] array = {4,6,8,5,9};
        HeapSort.sort(array);

        int[] result = {4,5,6,8,9};
        assertArrayEquals(array, result);
    }
}