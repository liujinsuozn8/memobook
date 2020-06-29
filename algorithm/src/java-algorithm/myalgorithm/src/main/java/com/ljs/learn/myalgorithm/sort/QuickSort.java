package com.ljs.learn.myalgorithm.sort;

import java.util.Arrays;

// 快速排序
public class QuickSort {
    public static void sort(int[] array, int left, int right){
        // 分别保存作右下标
        int l = left;
        int r = right;

        // 计算中轴值
        int pivot = array[(left + right) / 2];

        // 开始循环
        // 比 pivot小的，放在 pivot 的左边
        // 比 pivot大的，放在 pivot 的右边
        int temp;
        while(l < r){
            // 从左边开始，从左向右
            // 搜索比 pivot 大的值，准备放到 pivot的右侧
            while( array[l] < pivot ){
                l++;
            }
            // 从右边开始，从右向左
            // 搜索比 pivot 小的值，准备放到 pivot的左侧
            while( array[r] > pivot ){
                r--;
            }

            // 如果 l>= r，说明 pivot 的左右两边的数已经划分好了
            if (l >= r){
                break;
            }

            // 交换数据
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            // 交换完成之后，如果 arr[l] == pivot
            if (array[l] == pivot){
                r--;
            }

            // 交换完成之后，如果 arr[r] == pivot
            if (array[r] == pivot){
                l++;
            }

            // 如果 l == r，必须 l++， r--，否则会出现栈溢出
            if (l == r){
                l++;
                r--;
            }

            /*// 向左递归
            if (left < r){
                sort(array, left, r);
            }

            // 向右递归
            if (right > l){
                sort(array, l, right);
            }*/
        }
    }

    public static void sortByRight(int[] array, int left, int right){
        // 分别保存左右下标
        int l = left;
        int r = right;

        // 保存中轴值
        int pivot = array[r];

        // 比 pivot小的，放在 pivot 的左边; 比 pivot大的，放在 pivot 的右边
        while(l < r){
            // 从左边开始，从左向右，搜索比 pivot 大的值，准备放到 pivot的右侧
            // 与pivot相等时，保持原位
            while( array[l] <= pivot && l < r ) l++;
            array[r] = array[l];

            // 从右边开始，从右向左，搜索比 pivot 小的值，准备放到 pivot的左侧
            // 与pivot相等时，保持原位
            while( array[r] >= pivot && l < r ) r--;
            array[l] = array[r];
        }

        // 设置 pivot
        array[r] = pivot;

        // 左侧递归
        if (left < r-1) {
            sortByRight(array, left, r-1);
        }

        // 右侧递归
        if (r+1 > right){
            sortByRight(array, r+1, right);
        }
    }
}
