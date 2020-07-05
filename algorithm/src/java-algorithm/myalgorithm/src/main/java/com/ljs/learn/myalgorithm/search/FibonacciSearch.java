package com.ljs.learn.myalgorithm.search;

import java.util.Arrays;

public class FibonacciSearch {
    public static int fibMaxSize = 20;

    // 依照数列最大长度创建数列
    public static int[] fib() {
        int[] f = new int[20];
        f[0] = 1;
        f[1] = 2;

        for (int i = 2; i < fibMaxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /**
     * 斐波那契查找算法
     *
     * @param array   序列
     * @param findVal 目标值
     * @return 目标值所在下标。如果未找到，则返回 -1
     */
    public static int search(int[] array, int findVal) {
        int left = 0;
        int right = array.length - 1;
        int[] f = fib();

        // 1. 搜索能够覆盖 right 的最小斐波那契数列值
        int k = 0;
        while (f[k] < right) k++;

        // 2. 检查 right 和 f[k] 的大小，如果 right的长度不足，则扩充数组
        int[] searchArray;
        if (right == f[k]) {
            searchArray = array;
        } else {
            // 扩充数组
            searchArray = Arrays.copyOf(array, f[k]);
            // 将扩充部分的值设置为原始数组的right位置的值
            for (int i = right + 1; i < f[k]; i++) {
                searchArray[i] = array[right];
            }
        }

        // 3. 按照斐波那契数列的值来搜索
        int mid;
        int midVal;
        while (left <= right) {
            mid = left + f[k - 1] - 1;
            midVal = searchArray[mid];

            if (findVal < midVal) {
                // 向左搜索
                right = mid - 1;

                // 将整体长度调整为 F(K - 1) - 1
                k--;
            } else if (findVal > midVal) {
                // 向右搜索
                left = mid + 1;

                // 将整体长度调整为 F(K - 2) - 1
                k -= 2;
            } else {
                // 找到了目标值，需要检查返回哪个值
                // mid 与 原始数组的最大值比较
                if (mid <= array.length - 1) {
                    // 小于等于，表示mid仍然在原始数组的长度范围内，直接返回 mid
                    return mid;
                } else {
                    // 大于，表示mid的值处在扩充位置中
                    // 扩充位置的值都是原始数组 的最后一个元素，
                    // 所以返回 原始数组的最后一个index
                    return array.length - 1;
                }
            }
        }

        return -1;
    }

}
