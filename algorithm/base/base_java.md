<span id="catalog"></span>
- 参考
    - https://www.bilibili.com/video/BV1E4411H73v

### 目录
- [数据结构与算法概述](#数据结构与算法概述)
- [数据结构-数据结构分类](#数据结构-数据结构分类)
- 数据结构-稀疏数组
    - [引入问题-五子棋程序](#引入问题-五子棋程序)
    - [稀疏数组简介](#稀疏数组简介)
    - [稀疏数组原理](#稀疏数组原理)
    - [稀疏数组的实现](#稀疏数组的实现)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)


# 数据结构与算法概述
[top](#catalog)
- 程序 = 数据结构 + 算法
- 数据结构是算法的基础
- 两种类型的数据结构
    - 线性结构
    - 非线性结构

# 数据结构-数据结构分类
[top](#catalog)
- 两种类型的数据结构
    - 线性结构
    - 非线性结构
    
- 线性结构
    - 最常用的数据结构
    - 特点
        - 数据元素之间存在一对一的线性关系
    - 线性结构的两种**存储结构**
        - 顺序存储结构--顺序表
            - 表中的元素是顺序的
        - 链式存储结构--链表
            - 表中存储的元素不一定连续
            - 元素结点中存放数据元素以及相邻元素的地址信息
    - 常见线性结构
        - 数组
        - 队列
        - 链表
        - 栈
- 非线性结构
    - 常见非线性结构
        - 二维数组
        - 多维数组
        - 广义表
        - 树结构
        - 图结构

# 数据结构-稀疏数组
## 引入问题-五子棋程序
[top](#catalog)
- 五子棋程序的需求
    - 要有 存盘退出 和 续上盘 的功能
    - 棋盘大小为：11 x 11
    - 两种棋子：黑子和白子
- 传统的解决方法
    - 使用 11 x 11 的二维数组记录棋盘
    - 使用数值表示棋盘上某个位置的状态
        - 0，表示没有棋子
        - 1，表示黑子
        - 2，表示白子
- 传统方式的问题
    - 在一局棋的很长时间内，绝大部分位置是空白的，使用二维数组会存储很多无意义的数据，即 `0`
- 解决方法
    - 使用稀疏数组对数据进行压缩

## 稀疏数组简介
[top](#catalog)
- 稀疏数组，sparse array
- 适用场景
    - 一个数组中大部分元素为0、或者同一个值
- 稀疏数组的作用
    - 把具有不同值的元素的行列及值记录在一个小规模的数组中，缩小程序的规模

## 稀疏数组原理
[top](#catalog)
- 稀疏数组中保存的内容
    1. 第一个元素记录：
        - 数组的行数
        - 数组的列数
        - 有多少不同的值
    2. 之后的元素以 `行idnex, 列index, value` 的方式记录元素

- 稀疏数组示例
    - 原始数组：7 x 6
    
        ||||||||
        |-|-|-|-|-|-|-|
        |0|0|0|22|0|0|15|
        |0|11|0|0|0|17|0|
        |0|0|0|-6|0|0|0|
        |0|0|0|0|0|39|0|
        |91|0|0|0|0|0|0|
        |0|0|28|0|0|0|0|

    - 转换后的稀疏数组

        ||行|列|值|
        |-|-|-|-|
        |`[0]`|6|7|8|
        |`[1]`|0|3|22|
        |`[2]`|0|6|22|
        |`[3]`|1|1|11|
        |`[4]`|1|5|17|
        |`[5]`|2|3|-6|
        |`[6]`|3|5|39|
        |`[7]`|4|0|91|
        |`[8]`|5|2|28|

- 二维数组 与 稀疏数组的相互转换方法
    - 二维数组 --> 稀疏数组
        1. 遍历原始的二维数组，获取有效数据的个数 sum
        2. 根据 sum 就可以创建稀疏数组 `sparseArray[sum + 1][3]`
        3. 将二维数组中的有效数据保存到稀疏数组中
    - 稀疏数组 --> 二维数组
        1. 根据稀疏数组的第一行，创建原始的二维数组
        2. 再读取稀疏数组剩余行的数组，并赋值给原始的二维数组
        
## 稀疏数组的实现
[top](#catalog)
- 实现部分
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/sparsearray/SparseArray.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/sparsearray/SparseArray.java)
    - 代码内容
        ```java
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
        ```
- 测试部分
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/sparsearray/SparseArrayTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/sparsearray/SparseArrayTest.java)
    - 测试内容
        ```java
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
        ```

[top](#catalog)