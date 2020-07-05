package com.ljs.learn.myalgorithm.search;

// 顺序查找
public class SeqSearch {
    // 依次遍历序列的每个元素查找指定值的索引
    public static int search (int[] array, int target){
        for (int i = 0; i<array.length; i++){
            if (target == array[i]){
                return i;
            }
        }
        return -1;
    }
}
