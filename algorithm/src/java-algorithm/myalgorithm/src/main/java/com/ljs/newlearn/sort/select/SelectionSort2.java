package com.ljs.newlearn.sort.select;

/**
 * 选择排序2 一次遍历时，同时搜索最大和最小的值
 */
public class SelectionSort2 {
    public static void main(String[] args) {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1,0};


        for (int j = 0; j < array.length / 2 ; j++) {
            int minPos = j;
            int maxPos = array.length - j - 1;
            for (int i = j + 1; i < array.length - j; i++) {
                if (array[i] < array[minPos]) {
                    minPos = i;
                    continue;
                }

                if (array[i] > array[maxPos]) {
                    maxPos = i;
                }
            }

            swap(array, j, minPos);
            swap(array, array.length - j - 1, maxPos);
            printArray(array);
        }

        // printArray(array);
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
