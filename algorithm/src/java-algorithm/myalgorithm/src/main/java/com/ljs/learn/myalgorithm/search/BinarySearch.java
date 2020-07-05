package com.ljs.learn.myalgorithm.search;

import java.util.ArrayList;
import java.util.List;

// 二分查找
public class BinarySearch {
    // 1. 启动基本的升序二分查找
    public static int search(int[] array, int findVal){
        return searchASC(array, 0, array.length - 1, findVal);
    }

    /** 1. 对升序序列进行二分查找--基本实现
     *
     * @param array     有序的升序序列
     * @param left      左侧索引
     * @param right     右侧索引
     * @param findVal   需要超找的值
     * @return          目标值的索引，没有找到返回-1
     */
    public static int searchASC(int[] array, int left, int right, int findVal){
        // 检查是否遍历完，如果遍历完，则没有找到，返回 -1
        if (left > right || findVal < array[left] || findVal > array[right]){ return -1; }

        // 计算中间位置
        int mid = (left + right) / 2;
        int midVal = array[mid];

        // 需要查找的值与中间值比较
        if (findVal < midVal){
            // 继续向左递归
            return searchASC(array, left, mid -1, findVal);
        } else if (findVal > midVal){
            // 继续向右递归
            return searchASC(array, mid + 1, right, findVal);
        } else {
            // 找到目标值，返回
            return mid;
        }
    }

    // 2. 启动扩展的升序二分查找
    public static List<Integer> searchAll(int[] array, int findVal){
        return searchASCAll(array, 0, array.length - 1, findVal);
    }

    // 2. 升序二分查找的扩展--查找所有相同的值
    public static List<Integer> searchASCAll(int[] array, int left, int right, int findVal){
        // 如果已经遍历完没有找到，则返回空对象
        if (left > right || findVal < array[left] || findVal > array[right]){ return null; }

        // 计算中间索引
        int mid = (left + right) / 2;
        int midVal = array[mid];

        // 需要超找的值与中间值比较
        if (findVal < midVal){
            // 继续向左递归
            return searchASCAll(array, left, mid - 1, findVal);
        } else if (findVal > midVal){
            // 继续向右递归
            return searchASCAll(array, mid + 1, right, findVal);
        } else {
            // 已经找到目标值
            List<Integer> idxList = new ArrayList<>();

            // 1. 向左侧(包括当前mid)搜索所有与目标值相等的元素索引
            // 先搜索，后保存，保证结果有序
            int temp = mid;
            while(temp >= 0 && array[temp] == findVal) temp--;

            // 将 [temp+1～mid]的index保存到数组，会同时保存mid
            for ( temp++; temp <= mid; temp++){
                idxList.add(temp);
            }

            // 2. 向右侧搜索所有与目标值相等的元素索引
            for (temp = mid + 1; mid < array.length; temp++){
                if (array[temp] == findVal){
                    idxList.add(temp);
                } else {
                    //如果值不同，说明已经没有相同元素了，终止左侧的遍历
                    break;
                }
            }

            return idxList;
        }
    }
}
