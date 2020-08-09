package com.ljs.learn.myalgorithm.dynamic;

import java.util.Arrays;

public class Knapsack {
    public static void main(String[] args) {
        work();
    }
    public static void work(){
        // 物品的重量
        int[] w = {1, 4, 3};
        // 物品的价值
        int[] val = {1500, 3000, 2000};

        int m = 4; // 背包的容量
        int n = val.length; // 物品的个数

        // 创建二维数组，表示表格
        // +1 表示默认为0的第一行和第一列
        int[][] v = new int[n+1][m+1];

        // 创建一个和表格大小相同二维数组
        // 用于记录每个单元格中放入的物品的组合
        int[][] path = new int[n+1][m+1];

        // 根据公式进行动态规划处理，需要跳过第一行和第一列
        for (int i=1; i < v.length; i++){
            for(int j=1;j<v[0].length;j++){
                if (w[i - 1] > j){
                    // 如果物品的容量超过的当前允许的最大容量，
                    // 则直接拷贝上一次的结果
                    v[i][j] = v[i-1][j];

                    // 因为没有使用新物品，所以物品组合中记为0
                    path[i][j] = 0;
                } else {
                    // v[i][j] = Math.max(v[i-1][j], val[i-1] + v[i-1][j-w[i-1]]);

                    if (v[i-1][j] > val[i-1] + v[i-1][j-w[i-1]]){
                        // 则直接拷贝上一次的结果
                        v[i][j] = v[i-1][j];

                        // 因为没有使用新物品，所以物品组合中记为0
                        path[i][j] = 0;
                    } else {
                        // 添加了新物品，所以物品组合中记为1
                        // 则直接拷贝上一次的结果
                        v[i][j] = val[i-1] + v[i-1][j-w[i-1]];

                        // 因为没有使用新物品，所以物品组合中记为0
                        path[i][j] = 1;
                    }
                }
            }
        }

        // 输出当前表格
        for(int i=0; i<v.length; i++){
            System.out.println(Arrays.toString(v[i]));
        }
        for(int i=0; i<path.length; i++){
            System.out.println(Arrays.toString(path[i]));
        }

        // 输出添加物品的组合
        int i = path.length - 1; // i为物品的个数，即path的行数
        int j = path[0].length - 1; // j为最大的容量，即path的列数

        // 倒叙遍历path
        while (i>0 && j>0){ // 不需要等于0，第一行第一列都是0，没有意义
            if (path[i][j] == 1){
                System.out.println("放入物品：" + i);
                j -= w[i-1];
            }
            i--;
        }
    }
}
