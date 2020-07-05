package com.ljs.learn.myalgorithm.search;

import java.util.ArrayList;
import java.util.List;

public class InsertSearch {
    // 启动插值查找的基本实现
    public static int search(int[] array, int findVal){
        return searchASC(array, 0, array.length - 1, findVal);
    }

    /** 1. 对升序序列进行插值查找--基本实现
     *
     * @param array     有序的升序序列
     * @param left      左侧索引
     * @param right     右侧索引
     * @param findVal   需要超找的值
     * @return          目标值的索引，没有找到返回-1
     */
    public static int searchASC(int[] array, int left, int right, int findVal){
        if (left > right || findVal < array[left] || findVal > array[right]){
            return -1;
        }

        int mid = left + (right - left) * (findVal - array[left]) / (array[right] - array[left]);
        int midVal = array[mid];

        if(findVal < midVal){
            return searchASC(array, left, mid -1, findVal);
        } else if (findVal > midVal){
            return searchASC(array, mid + 1, right, findVal);
        } else {
            return mid;
        }
    }

    // 2. 启动扩展的升序插值查找
    public static List<Integer> searchAll(int[] array, int findVal){
        return searchASCAll(array, 0, array.length - 1, findVal);
    }

    // 2. 升序插值查找的扩展--查找所有相同的值
    public static List<Integer> searchASCAll(int[] array, int left, int right, int findVal){
        if (left > right || findVal < array[left] || findVal > array[right]){
            return null;
        }

        int mid = left + (right - left) * (findVal - array[left]) / (array[right] - array[left]);
        int midVal = array[mid];

        if(findVal < midVal){
            return searchASCAll(array, left, mid -1, findVal);
        } else if (findVal > midVal){
            return searchASCAll(array, mid + 1, right, findVal);
        } else {
            List<Integer> indexes = new ArrayList<>();

            // 1. 向左侧(包括当前mid)搜索所有与目标值相等的元素索引
            // 先搜索，后保存，保证结果有序
            int temp = mid;
            while (temp >= 0 && array[temp] == findVal)temp--;

            // 将 [temp+1～mid]的index保存到数组，会同时保存mid
            for (temp++; temp <= mid; temp++){
                indexes.add(temp);
            }

            // 2. 向右侧搜索所有与目标值相等的元素索引
            for (temp = mid + 1; temp <= right; temp++){
                if (array[temp] == findVal){
                    indexes.add(temp);
                } else {
                    break;
                }
            }
            return indexes;
        }
    }

}
