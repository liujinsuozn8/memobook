package com.ljs.learn.myalgorithm.sort;

import java.util.Arrays;

// 插入排序
public class InsertSort {
    // 插入排序--分步求解
    public static void sortStep(int[] array){

        // 1. 第1轮
        // 定义待插入的数
        int insertVal = array[1];
        // 将搜索的起始位置定位到有序表的最后一个元素
        int insertIndex = 1 - 1;

        // 搜索 insertVal的插入位置
        // insertIndex >= 0 保证不越界
        // insertVal < array[insertIndex] 表示待插入的数还没有找到插入位置
        // 每次将 insertIndex元素 后移
        while(insertIndex >= 0 && insertVal < array[insertIndex]){
            array[insertIndex + 1] = array[insertIndex];
            insertIndex--;
        }

        // 退出循环时，说明已找到插入位置，插入元素
        array[insertIndex + 1] = insertVal;

        System.out.println("第1轮" + Arrays.toString(array));

        // 2. 第2轮
        insertVal = array[2];
        insertIndex = 2 - 1;
        while(insertIndex >= 0 && insertVal < array[insertIndex]){
            array[insertIndex + 1] = array[insertIndex];
            insertIndex--;
        }

        array[insertIndex + 1] = insertVal;
        System.out.println("第2轮" + Arrays.toString(array));

        // 3. 第3轮
        insertVal = array[3];
        insertIndex = 3 - 1;
        while(insertIndex >= 0 && insertVal < array[insertIndex]){
            array[insertIndex + 1] = array[insertIndex];
            insertIndex--;
        }

        array[insertIndex + 1] = insertVal;
        System.out.println("第3轮" + Arrays.toString(array));
    }

    // 插入排序实现（升序排列）
    public static void sort(int[] array){
        int n = array.length;
        int insertVal, insertIndex;
        for (int i = 1; i < n; i++) {
            // 1. 初始化数据
            insertVal = array[i];  // 无序表的第一个元素
            insertIndex = i - 1;   // 将搜索的起始位置定位到有序表的最后一个元素

            // 2. 搜索 insertVal的插入位置，并移动元素
            // insertIndex >= 0 保证不越界
            // insertVal < array[insertIndex] 表示待插入的数还没有找到插入位置
            // 每次将 insertIndex元素 前移
            while (insertIndex >= 0 && insertVal < array[insertIndex]){
                array[insertIndex + 1] = array[insertIndex];
                insertIndex--;
            }

            // 3. 插入元素
            array[insertIndex + 1] = insertVal;
        }
    }
}
