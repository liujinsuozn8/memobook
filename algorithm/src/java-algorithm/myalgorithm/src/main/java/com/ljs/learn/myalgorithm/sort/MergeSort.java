package com.ljs.learn.myalgorithm.sort;

// 归并排序
public class MergeSort {
    // 1. 启动排序
    public static void sort(int[] array){
        // 根据原始序列的大小创建临时数组
        int[] temp = new int[array.length];
        // 开始拆分，拆分到不可分后，回溯并执行合并
        split(array, 0, array.length-1, temp);
    }

    // 2. 归并排序的拆分阶段
    public static void split(int[] array, int startIdx, int endIdx, int[] temp){
        // startIdx < endIdx，说明当前要拆分的子序列中还有至少2个元素
        // 如果不满足，则说明当前子序列中只有一个元素
        if (startIdx < endIdx) {
            int midIdx = (startIdx + endIdx) / 2;
            // 向左拆分
            split(array, startIdx, midIdx, temp);

            // 向右拆分
            split(array, midIdx + 1, endIdx, temp);

            // 左边和右边拆分完成之后，执行合并
            merge(array, startIdx, midIdx, endIdx, temp);
        }
    }

    /** 3. 归并排序的合并阶段
     *
     * @param array     原始数组
     * @param startIdx  左指针的起始位置
     * @param midIdx    左指针的结束位置
     * @param endIdx    右指针的结束位置
     * @param temp      保存数据的临时数组
     */
    public static void merge(int[] array, int startIdx, int midIdx, int endIdx, int[] temp){
        // 划分左右指针
        int left = startIdx;
        int right = midIdx + 1;
        //  初始化 temp 的起始索引
        int t = 0;

        // 比较left和right所指向的元素，并保存到temp中
        // 直到左侧或右侧结束
        while (left <= midIdx && right <= endIdx){
            if(array[left] <= array[right]){
                temp[t] = array[left];
                t++;
                left++;
            } else {
                temp[t] = array[right];
                t++;
                right++;
            }
        }

        // 将left或right中剩余的部分保存到temp中
        while ( left <= midIdx ){
            temp[t] = array[left];
            t++;
            left++;
        }
        while ( right <= endIdx ){
            temp[t] = array[right];
            t++;
            right++;
        }

        // 将temp中有序的数据拷贝到原始数组中
        left = startIdx;
        t = 0;
        while (left <= endIdx){
            array[left] = temp[t];
            t++;
            left++;
        }
    }
}
