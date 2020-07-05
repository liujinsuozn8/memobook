package com.ljs.learn.myalgorithm.sort;

import java.util.Arrays;

public class RadixSort {
    // 分步求解
    public static void sortStep(int[] array){
        // 创建桶
        int[][] bucket = new int[10][array.length];
        // 创建一个一维数组，保存各个桶的元素个数
        int[] bucketElemCount = new int[10];

        // 1. 第1轮，遍历个位数
        // 1.1 遍历序列，比根据【个位】的值，放入不同的桶中
        int digit = 0; // 记录当前元素的某个数位的值
        for (int e : array) {
            // 计算个位的值
            digit = e%10;
            // 保存到桶中，同时记录桶中元素的个数
            bucket[digit][bucketElemCount[digit]] = e;
            bucketElemCount[digit]++;
        }

        // 1.2 遍历所有的桶，并将桶中的数据按顺序拷贝到原始数组
        int originIndex = 0;    // 记录将数据拷贝到原始数组时的索引
        for (int i = 0; i < 10; i++) {
            // 如果桶中没有数据，就跳过
            if (bucketElemCount[i] <= 0) continue;
            // 如果桶中有数据，就进行拷贝
            for (int j = 0; j < bucketElemCount[i]; j++) {
                array[originIndex] = bucket[i][j];
                originIndex++;
            }
            // 清空记录数量
            bucketElemCount[i] = 0;
        }

        System.out.println("第1轮，个位: " + Arrays.toString(array));

        // 2. 第2轮，遍历十位数
        // 2.1 遍历序列，比根据【十位】的值，放入不同的桶中
        digit = 0; // 记录当前元素的某个数位的值
        for (int e : array) {
            // 计算十位的值
            digit = e/ 10 % 10;         // <<<<<<<<<<<<<<<<<<<< 发生修改的代码
            // 保存到桶中，同时记录桶中元素的个数
            bucket[digit][bucketElemCount[digit]] = e;
            bucketElemCount[digit]++;
        }

        // 2.2 遍历所有的桶，并将桶中的数据按顺序拷贝到原始数组
        originIndex = 0;    // 记录将数据拷贝到原始数组时的索引
        for (int i = 0; i < 10; i++) {
            // 如果桶中没有数据，就跳过
            if (bucketElemCount[i] <= 0) continue;
            // 如果桶中有数据，就进行拷贝
            for (int j = 0; j < bucketElemCount[i]; j++) {
                array[originIndex] = bucket[i][j];
                originIndex++;
            }
            // 清空记录数量
            bucketElemCount[i] = 0;
        }

        System.out.println("第2轮，十位: " + Arrays.toString(array));        // 2. 第1轮，遍历百位数

        // 3. 第3轮，遍历百位数
        // 3.1 遍历序列，比根据【百位】的值，放入不同的桶中
        digit = 0; // 记录当前元素的某个数位的值
        for (int e : array) {
            // 计算百位的值
            digit = e/ 100 % 10;         // <<<<<<<<<<<<<<<<<<<< 发生修改的代码
            // 保存到桶中，同时记录桶中元素的个数
            bucket[digit][bucketElemCount[digit]] = e;
            bucketElemCount[digit]++;
        }

        // 3.2 遍历所有的桶，并将桶中的数据按顺序拷贝到原始数组
        originIndex = 0;    // 记录将数据拷贝到原始数组时的索引
        for (int i = 0; i < 10; i++) {
            // 如果桶中没有数据，就跳过
            if (bucketElemCount[i] <= 0) continue;
            // 如果桶中有数据，就进行拷贝
            for (int j = 0; j < bucketElemCount[i]; j++) {
                array[originIndex] = bucket[i][j];
                originIndex++;
            }
            // 清空记录数量
            bucketElemCount[i] = 0;
        }

        System.out.println("第3轮，百位: " + Arrays.toString(array));
    }

    // 排序实现
    public static void sort(int[] array){
        // 获取最大值的位数
        int max = array[0];
        for (int e : array) {
            if (max < e) { max = e; }
        }
        // 将数字转换位字符串，再计算字符串的长度，即为最大值的位数
        int maxDigit = (max + "").length();

        // 创建桶
        int[][] bucket = new int[10][array.length];
        // 创建一个一维数组，保存各个桶的元素个数
        int[] bucketElemCount = new int[10];
        // 保存当前元素的某个数位的值
        int digit = 0;
        // 保存将数据拷贝到原始数组时的索引
        int originIndex = 0;

        // 遍历各个位数，同时计算获取所需位数需要除掉的值
        for (int d = 0, n = 1; d < maxDigit; d++, n*=10) {
            // 1 遍历序列，比根据各个数位的值，放入不同的桶中
            for (int e : array) {
                // 计算个位的值
                digit = e / n %10;
                // 保存到桶中，同时记录桶中元素的个数
                bucket[digit][bucketElemCount[digit]] = e;
                bucketElemCount[digit]++;
            }

            // 2 遍历所有的桶，并将桶中的数据按顺序拷贝到原始数组
            originIndex = 0;
            for (int i = 0; i < 10; i++) {
                // 如果桶中没有数据，就跳过
                if (bucketElemCount[i] <= 0) continue;
                // 如果桶中有数据，就进行拷贝
                for (int j = 0; j < bucketElemCount[i]; j++) {
                    array[originIndex] = bucket[i][j];
                    originIndex++;
                }
                // 清空记录数量
                bucketElemCount[i] = 0;
            }
        }
    }
}
