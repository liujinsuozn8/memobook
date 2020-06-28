package com.ljs.learn.myalgorithm.sort;

import java.util.Arrays;

// 希尔排序
public class ShellSort {
    public static void sortStep(int[] array){
        int temp;
        int n = array.length;

        // 1. 第 1 轮
        for (int i=5; i<n; i++){
            // 按照步长为5，倒序扫描前面所有的元素
            for(int j=i-5; j>=0; j-=5){
                // 当前元素，与加步长后的元素进行比较
                // 如果当前元素步后面的元素大，则交换
                if (array[j] > array[j + 5]){
                    temp = array[j];
                    array[j] = array[j + 5];
                    array[j + 5] = temp;
                } else {
                    break;
                }
            }
        }

        System.out.println("第1轮: "+Arrays.toString(array));

        // 2. 第2轮
        for (int i=2; i<n; i++){
            // 按照步长为2，倒序扫描前面所有的元素
            for(int j=i-2; j>=0; j-=2){
                // 当前元素，与加步长后的元素进行比较
                // 如果当前元素步后面的元素大，则交换
                if (array[j] > array[j + 2]){
                    temp = array[j];
                    array[j] = array[j + 2];
                    array[j + 2] = temp;
                } else {
                    break;
                }
            }
        }

        System.out.println("第2轮: "+Arrays.toString(array));

        // 第3轮
        for (int i=1; i<n; i++){
            // 按照步长为1，倒序扫描前面所有的元素
            for(int j=i-1; j>=0; j-=1){
                // 当前元素，与加步长后的元素进行比较
                // 如果当前元素步后面的元素大，则交换
                if (array[j] > array[j + 1]){
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                } else {
                    break;
                }
            }
        }

        System.out.println("第3轮: "+Arrays.toString(array));
    }

    // 交换式希尔排序
    public static void sortBySwap(int[] array){
        int temp;
        int n = array.length;
        // 计算增量，直到增量减小为1
        for (int gap=n/2; gap > 0; gap/=2){
            // 从增量位置的元素开始遍历，遍历到最后一个元素
            for (int i=gap; i<n; i++){
                // 依照步长遍历元素前面的元素，比较大小，并执行插入操作
                for (int j=i-gap; j>=0; j-=gap){
                    // 当前元素，与加步长后的元素进行比较
                    // 如果当前元素步后面的元素大，则交换
                    if (array[j] > array[j + gap]){
                        // 移动元素
                        temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    } else {
                        break;  // 前面的元素已经有序，立刻停止
                    }
                }
            }
        }
    }

    // 移动式希尔排序
    public static void sortByMove(int[] array){
        int insertVal, insertIndex;
        int n = array.length;

        // 计算增量，直到增量减小为1
        for (int gap=n/2; gap > 0; gap/=2){
            // 从增量位置的元素开始遍历，遍历到最后一个元素
            for (int i=gap; i < n; i++){
                insertVal = array[i];
                insertIndex = i-gap;
                while(insertIndex >= 0 && insertVal < array[insertIndex]){
                    array[insertIndex + gap] = array[insertIndex]; // 移动元素
                    insertIndex -= gap;
                }
                array[insertIndex + gap] = insertVal;
            }
        }
    }
}
