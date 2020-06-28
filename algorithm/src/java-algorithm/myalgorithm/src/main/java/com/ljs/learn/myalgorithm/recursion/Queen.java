package com.ljs.learn.myalgorithm.recursion;

public class Queen {
    // 定义一个MAX，表示共有多少个皇后
    private int max = 8;
    // 定义数组array，保存皇后的位置，如: arr[8] = [0, 2, 4, 1, 3, 5, 7, 6]
    private int[] array = new int[max];
    // 记录有效的摆放次数
    public int count = 0;

    // 1. 放置第n个皇后
    public void check(int n){
        if (n == max){ // n = 8时，正在放第9个皇后，说明前面8个皇后已经放好了
            print(); //输出摆放结果
            return ;
        }
        // 1. 从第0列到第7类依次尝试放入皇后，并判断是否冲突
        // 循环中没有 break，所以会不停的回溯直到找出：所有的摆放方法
        for (int i = 0; i < max; i++) {
            array[n] = i;
            if (judge(n)){ // 2. 如果不冲突，再放第n+1个皇后
                check(n+1);
            }
            // 3. 如果冲突，尝试将皇后放到下一列
        }
    }

    // 2. 检测第n个皇后，是否与前面的皇后冲突
    private boolean judge(int n){
        for (int i = 0; i < n; i++) {
            // 1. array[i] == array[n]: 判断第n个皇后是否会和 前面的 n-1个皇后在同一列
            // 2. Math.abs(n-i) == Math.abs(array[n] - array[i])，在斜线上是否冲突
            //      即两个皇后的行间距是否等于列间距，也可以理解为斜率的绝对值不等于 1
            // 3. n 每次都在递增，即行在递增，所以不需要判断行是否冲突
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    // 打印皇后摆放的位置
    private void print(){
        count++; // 每次打印时，统计有效的摆放次数
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
