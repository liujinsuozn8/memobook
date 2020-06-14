package com.ljs.learn.datastructure.sparsearray;

import org.junit.Test;

import java.util.Arrays;

public class SparseArrayTest {
    @Test
    public void test01(){
        // 1. 创建 11 x 11的二维数组
        // - 0，表示没有棋子
        // - 1，表示黑子
        // - 2，表示白子
        int originArr[][] = new int[11][11];
        originArr[1][2] = 1;
        originArr[2][4] = 2;

        // 2. 输出二维数组
        for (int[] row : originArr) {
            System.out.println(Arrays.toString(row));
        }

        // 3. 将二维数组转换为稀疏数组
        int[][] sparse = SparseArray.arrayToSparse(originArr);
        for (int[] row : sparse) {
            System.out.println(Arrays.toString(row));
        }

        // 4. 将稀疏数组转换为二维数组
        int[][] array02 = SparseArray.sparseToArray(sparse);
        for (int[] row : array02) {
            System.out.println(Arrays.toString(row));
        }
    }
}
