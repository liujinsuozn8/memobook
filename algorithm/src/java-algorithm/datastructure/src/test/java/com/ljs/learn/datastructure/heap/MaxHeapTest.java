package com.ljs.learn.datastructure.heap;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MaxHeapTest {
    // 测试整个树的大顶堆构建
    @Test
    public void adjustHeap() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        MaxHeap.adjustHeap(array);
        int[] result = {11, 10, 7, 9, 5, 6, 3, 8, 4, 2, 1};

        assertArrayEquals(array, result);
    }

    // 测试子树部分的大顶堆构建
    @Test
    public void adjustHeapForSubTree() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        // 计算最后一个非叶子结点的索引
        int lastNoLeafNodeIdx = array.length / 2 - 1;
        MaxHeap.adjustHeapForSubTree(array, lastNoLeafNodeIdx, array.length);

        int[] result = {1, 2, 3, 4, 11, 6, 7, 8, 9, 10, 5};

        assertArrayEquals(array, result);
    }
}