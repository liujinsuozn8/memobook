package com.ljs.learn.myalgorithm.sort;

// 冒泡排序
public class BubbleSort {
    // 升序排列
    // 只进行排序
    public static void sort01(int[] array){
        int n  = array.length;
        int temp;
        boolean isSwap=false;
        // 整体大循环次数为 n-1
        // i可以理解跳过末尾的i个数字
        for (int i = 1; i < n; i++) {
            // 检查元素大小，并交换元素
            for (int j = 0; j < n-i; j++) { // 比较 n-i 次，跳过最后 i 个数字
                if (array[j] > array[j+1]){
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    // 升序排列
    // 排序并优化
    public static void sort02(int[] array){
        int n  = array.length;
        int temp;
        boolean isSwap=false;
        // 整体大循环次数为 n-1
        // i可以理解跳过末尾的i个数字
        for (int i = 1; i < n; i++) {
            // 1. 初始化元素交换标记
            isSwap = false;
            // 2. 检查元素大小，并交换元素
            for (int j = 0; j < n-i; j++) { // 比较 n-i 次，跳过最后 i 个数字
                if (array[j] > array[j+1]){
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;

                    // 每次发生元素交换后，修改标记
                    isSwap = true;
                }
            }

            // 3. 检查是否发生了元素交换，如果没有，则序列有序，停止排序
            if (!isSwap){ break; }
        }
    }
}
