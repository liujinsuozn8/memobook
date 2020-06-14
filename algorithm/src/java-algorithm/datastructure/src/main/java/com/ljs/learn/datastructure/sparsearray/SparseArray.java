package com.ljs.learn.datastructure.sparsearray;

import java.util.Arrays;

public class SparseArray {
    // - 二维数组 --> 稀疏数组
    public static int[][] arrayToSparse(int[][] array) {
        // 1. 遍历原始的二维数组，获取有效数据的个数 sum
        int sum = 0;
        for (int[] row : array) {
            for (int node : row) {
                if (node != 0)
                    sum++;
            }
        }

        // 2. 根据 sum 就可以创建稀疏数组 `sparseArray[sum + 1][3]`
        int[][] sparse = new int[sum + 1][3];

        // 3. 将二维数组中的有效数据保存到稀疏数组中
        // 3.1. 第一个元素记录：
        // - 数组的行数
        sparse[0][0] = array.length;
        // - 数组的列数
        sparse[0][1] = array[0].length;
        // - 有多少不同的值
        sparse[0][2] = sum;

        // 3.2. 之后的元素以 `行idnex, 列index, value` 的方式记录元素
        int num = 0;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[r].length; c++) {
                if (array[r][c] != 0){
                    num++;
                    sparse[num][0] = r;
                    sparse[num][1] = c;
                    sparse[num][2] = array[r][c];
                }
            }
        }

        return sparse;
    }

    // - 稀疏数组 --> 二维数组
    public static int[][] sparseToArray(int[][] sparse){
        // 1. 根据稀疏数组的第一行，创建原始的二维数组
        int[][] array = new int[sparse[0][0]][sparse[0][1]];

        // 2. 再读取稀疏数组剩余行的数组，并赋值给原始的二维数组
        for (int i = 1; i< sparse.length; i++) {
            array[sparse[i][0]][sparse[i][1]] = sparse[i][2];
        }

        return array;
    }
}
