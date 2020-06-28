package com.ljs.learn.myalgorithm.sort;

// 选择排序
public class SelectSort {
    // 选择排序的分部解法
    public static void sort_step(int[]array){
        int n = array.length;
        int minValueIdx, temp;

        // 1. 第 1 次
        // 假设当前元素是这一趟的最小值
        minValueIdx = 0;
        for (int i = minValueIdx + 1; i < n; i++) {
            // 搜索更小的元素
            if (array[minValueIdx] > array[i]){
                minValueIdx = i;
            }
        }

        // 检查最小值索引是否发生变化
        // 如果发生变化，说明后面有更小的元素，则交换元素
        if (minValueIdx != 0){
            temp = array[0];
            array[0] = array[minValueIdx];
            array[minValueIdx] = temp;
        }

        // 2. 第 2 次
        // 假设当前元素是这一趟的最小值
        minValueIdx = 1;
        for (int i = minValueIdx + 1; i < n; i++) {
            // 搜索更小的元素
            if (array[minValueIdx] > array[i]){
                minValueIdx = i;
            }
        }

        // 检查最小值索引是否发生变化
        // 如果发生变化，说明后面有更小的元素，则交换元素
        if (minValueIdx != 1){
            temp = array[1];
            array[1] = array[minValueIdx];
            array[minValueIdx] = temp;
        }
    }

    // 升序排列
    public static void sort(int[] array){
        int n = array.length;

        int minValueIdx, temp;
        for (int i = 0; i < n - 1; i++) {
            // 1. 假设当前元素是这一趟的最小值
            minValueIdx = i;
            for (int j = i+1; j < n; j++) {
                // 2. 如果有更小的元素，则重置索引
                if (array[minValueIdx] > array[j]){
                    minValueIdx = j;
                }
            }

            // 3. 检查最小值索引是否发生变化
            // 如果发生变化，说明后面有更小的元素，则交换元素
            if (minValueIdx != i){
                temp = array[i];
                array[i] = array[minValueIdx];
                array[minValueIdx] = temp;
            }
        }
    }
}
