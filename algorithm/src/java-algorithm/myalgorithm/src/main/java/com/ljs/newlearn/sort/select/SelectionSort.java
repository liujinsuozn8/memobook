package com.ljs.newlearn.sort.select;

/**
 * 选择排序
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {4, 3, 6, 2, 6, 4, 9, 1, 4};

        for (int i = 0; i < array.length - 1; i++) {
            int minPos = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[minPos] > array[j]) {
                    minPos = j;
                }
            }

            swap(array, minPos, i);
        }

        printArray(array);
    }

    public static void printArray(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void swap (int[] array, int i, int j){
        int temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
