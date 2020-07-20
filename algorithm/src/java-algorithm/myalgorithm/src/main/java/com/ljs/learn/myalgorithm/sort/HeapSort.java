package com.ljs.learn.myalgorithm.sort;

import java.util.Arrays;

public class HeapSort {

    // 分步执行堆排序
    public static void sortByStep(int[] array) {
        int temp;
        int length = array.length;
        // 1. 第一步
        // 1.1 构建大顶堆
        adjustHeap(array, length - 0);
        // 1.2 交换第一个与最后一个元素
        temp = array[0];
        array[0] = array[length - 0 - 1];
        array[length - 0 - 1] = temp;

        // 2. 第二步
        // 2.1 构建大顶堆
        adjustHeap(array, length - 1);
        // 2.2 交换第一个与倒数第二个元素
        temp = array[0];
        array[0] = array[length - 1 - 1];
        array[length - 1 - 1] = temp;
    }

    // 1. 执行堆排序
    public static void sort(int[] array) {
        int temp;

        // 每轮排序后，将大顶堆的构建长度 - 1，直到还剩一个元素
        for (int length = array.length; length > 1; length--) {
            // 1 构建大顶堆
            adjustHeap(array, length);
            // 2 交换第一个与最后一个元素
            temp = array[0];
            array[0] = array[length - 1];
            array[length - 1] = temp;
            // System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 2. 启动大顶堆的构建，可以部分构建
     *
     * @param array  需要构建大顶堆的数组
     * @param length 构建的最大长度，可以设置只将树的一部分构建为大顶堆
     */
    public static void adjustHeap(int[] array, int length) {
        // 1. 计算最后一个非叶子结点的位置
        int lastNoLeafNodeIdx = array.length / 2 - 1;

        // 2. 从后向前，将每个结点构建为大顶堆
        for (int i = lastNoLeafNodeIdx; i >= 0; i--) {
            adjustHeapForSubTree(array, i, length);
        }
    }

    /**
     * 3. 将 i 结点开始的子树调整为一个大顶堆
     * @param array 待调整的数组
     * @param i   表示非叶子结点在数组中的索引
     * @param length 对多少个元素进行调整
     */
    public static void adjustHeapForSubTree(int[] array, int i, int length) {
        // 1. 保存 i 结点的数据
        int temp = array[i];

        // 2. 调整堆
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 2.1 如果还有右子节点，并且右子节点更大，将k指向右子节点
            // 否则使用左结点
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }

            // 2.2 如果子节点大于 temp，交换位置
            if (array[k] > temp) {
                array[i] = array[k];
                i = k;
            } else {
                // 否则，整个子树已经是大顶堆，退出构建
                break;
            }
        }

        // 3. 以i为父节点子树构建完成，将temp保存到调整后的位置
        array[i] = temp;
    }
}
