package com.ljs.learn.myalgorithm.recursion;

import java.util.Arrays;

// 迷宫
public class Maze {
    public static void main(String[] args) {
        // 创建一个二维数组，模拟迷宫
        // 使用 1 表示墙
        int[][] maze = {
                {1,1,1,1,1,1,1},
                {1,0,0,0,0,0,1},
                {1,0,0,0,0,0,1},
                {1,1,1,0,0,0,1},
                {1,0,0,0,0,0,1},
                {1,0,0,0,0,0,1},
                {1,0,0,0,0,0,1},
                {1,1,1,1,1,1,1},
        };

        setWay(maze, 1, 1);

        // 打印搜索结果
        for (int[] row : maze) {
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * 使用递归搜索路径
     * 起点与终点
     * - (i,j) 表示出发的起点
     * - (6,5) 表示出发的终点
     *
     * 搜索中使用的标记
     * 1. 当 map[i][j] == 0，表示该点没有走过
     * 2. 当 map[i][j] == 1，表示墙壁
     * 3. 当 map[i][j] == 2，表示通路
     * 4. 当 map[i][j] == 3，表示该点已经走过，但是是死路
     *
     * 搜索策略
     * - 走迷宫时的方向：下 -> 右 -> 上 -> 左
     * - 走不通时，再回溯
     * @param map 表示地图
     * @param i 起点
     * @param j 起点
     * @return 找到路返回true，否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j){
        if (map[6][5] == 2){
            // 结束条件，即通路已经找到
            return true;
        }

        // 1. 检查当前路径走没走过
        if (map[i][j] == 0){
            // 如果当前点没有走过，按照搜索策略走：下 -> 右 -> 上 -> 左
            map[i][j] = 2; //先假设该点可以走通
            if (setWay(map, i+1, j)){ // 下
                return true;
            } else if (setWay(map, i, j+1)){ // 右
                return true;
            } else if (setWay(map, i-1, j)) { // 上
                return true;
            } else if (setWay(map, i, j-1)) { // 左
                return true;
            } else {
                // 都走不通说明该点是死路
                map[i][j] = 3;
                return false;
            }
        } else {
            // map[i][j] = 1, 2, 3
            // map[i][j] = 1 --> 表示碰到墙壁
            // map[i][j] = 2 --> 表示已经走过了，不要重复再走一步，防止死循环
            // map[i][j] = 3 --> 表示已经走过的死路

            // 每一种情况都是不能走的状态，所以返回false
            return false;
        }
    }
}
