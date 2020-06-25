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
- 数据结构-队列
    - [队列简介](#队列简介)
    - [数组模拟队列](#数组模拟队列)
    - [环状队列](#环状队列)
- 数据结构-单链表
    - [单链表简介](#单链表简介)
    - [单链表的注意事项](#单链表的注意事项)
    - [单链表的基本实现](#单链表的基本实现)
    - [有序单链表的实现](#有序单链表的实现)
    - [单链表常见问题](#单链表常见问题)
        - [单链表常见问题汇总](#单链表常见问题汇总)
        - [求单链表中有效结点的个数](#求单链表中有效结点的个数)
        - [查找单链表中的倒数第k个结点](#查找单链表中的倒数第k个结点)
        - [单链表的反转](#单链表的反转)
        - [逆序打印单链表](#逆序打印单链表)
        - [合并两个有序的单链表](#合并两个有序的单链表)
- 数据结构-双向链表
    - [双向链表简介](#双向链表简介)
    - [双向链表的基本实现](#双向链表的基本实现)
    - [有序双向链表的实现](#有序双向链表的实现)
- 数据结构-单向环形链表
    - [单向环形链表的实现](#单向环形链表的实现)
    - [单向环形链表的应用--Josephu问题](#单向环形链表的应用--Josephu问题)
- 数据结构-栈
    - [栈简介](#栈简介)
    - [使用数组实现栈](#使用数组实现栈)
    - [使用单链表实现栈](#使用单链表实现栈)
- 栈的应用--前缀中缀后缀表达式
    - [前缀中缀后缀表达式简介](#前缀中缀后缀表达式简介)
    - [中缀表达式应用--中缀表达式计算器](#中缀表达式应用--中缀表达式计算器)
        - [中缀表达式计算器--实现方式1--存在减号问题](#中缀表达式计算器--实现方式1--存在减号问题)
        - [中缀表达式计算器的求解示例](#中缀表达式计算器的求解示例)
        - [中缀表达式计算器--实现方式2--解决减号问题](#中缀表达式计算器--实现方式2--解决减号问题)
    - [后缀表达式应用--后缀表达式计算器](#后缀表达式应用--后缀表达式计算器)
        - [中缀表达式转后缀表达式的方法](#中缀表达式转后缀表达式的方法)
        - [中缀表达式转后缀表达式的实现](#中缀表达式转后缀表达式的实现)
        - [后缀表达式计算器的实现](#后缀表达式计算器的实现)
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

# 数据结构-队列
## 队列简介
[top](#catalog)
- 队列属于有序列表，可以用数组或链表来实现
- 队列遵循先入先出的原则

## 数组模拟队列
[top](#catalog)
- 数组模拟队列的示意图
    - ![imgs/data_structure/queue/array_queue.png](imgs/data_structure/queue/array_queue.png)
- 标记参数
    - maxSize，该队列的最大容量
    - front，表示队头的前一个位置
        - 随着数据输出而改变
        - 初始化为 -1，**指向队列头元素的前一个位置**
    - rear，表示队尾
        - 随着数据输入而改变
        - 初始化为 -1，**指向队列的尾部，就是指向队列的最后一个数据**
- 实现方法
    - 根据 maxSize 创建数组
    - 入队
        1. 检查队列是否已满
            - 如果 `rear == maxSize - 1`，则队列已满
        2. 入队时，rear 指针往后移动: `rear+1`，然后保存数据
    - 出队
        1. 检查队列是否为空
            - 当 `front == rear` 时，队列为空
        2. 出队时，front 指针往后移动: `front+1`，然后返回指向的元素
            - front指向的位置是对头的前一个位置
- 实现方法的问题
    - 队列只能被使用一次，无法复用
        - front 只负责向后移动，元素并没有清空
        - 当 `rear = maxSize - 1` 后，再也无法添加新的元素
    - 解决方法：环状队列
- 基本实现
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/queue/BaseArrayQueue.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/queue/BaseArrayQueue.java)
    - 实现内容
        ```java
        public class BaseArrayQueue<T> {
            // 标记参数
            // maxSize，该队列的最大容量
            private int maxSize;
            // front，表示队头。随着数据输出而改变
            private int front;
            // rear，表示队尾。随着数据输入而改变
            private int rear;
            // 保存队列元素
            private T[] container;
        
            public BaseArrayQueue(int maxSize) {
                this.maxSize = maxSize;
                // 根据 maxSize 创建数组
                container = (T[])new Object[maxSize];
                // 指向队列头的前一个位置
                front = -1;
                // 指向队列的尾部，就是队列的最后一个数据
                rear = -1;
            }
              
            // 检查队列是否已满
            public boolean isFull(){
                return rear == maxSize - 1;
            }
        
            // 检查队列是否为空
            public boolean isEmpty(){
                return front == rear;
            }
        
            // 入队
            public void add(T item){
                // 判断队列是否已满
                if (isFull()){
                    return ;
                }
        
                // 每次入队时，rear 指针往后移动: `rear+1`，然后保存数据
                rear++;
                container[rear] = item;
            }
        
            // 出队
            public T take(){
                // - 当 `front == rear` 时，队列为空
                if (isEmpty()){
                    return null;
                }
        
                // 每次出队时，front 指针往后移动: `front+1`，指向最新的元素
                front++;
                return container[front];
            }
        
            // 返回队头元素
            public T peek(){
                return container[front + 1];
            }
        
            public void showQueue(){
                for (int i = front+1; i<=rear; i++){
                    System.out.println(container[i]);
                }
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/queue/ArrayQueueTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/queue/ArrayQueueTest.java)
    - 测试代码
        ```java
        @Test
        public void test01(){
            BaseArrayQueue<Integer> queue = new BaseArrayQueue<>(4);
            queue.add(55);
            queue.add(33);
            queue.add(77);
            queue.add(66);
            queue.add(88);
    
            System.out.println("----showQueue----");
            queue.showQueue();
    
            System.out.println("----peek----");
            System.out.println(queue.peek());
    
            System.out.println("----take----");
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());

            // 队列已经失效
            queue.add(111);
            queue.add(222);

            System.out.println("----showQueue----");
            queue.showQueue();

            // ----showQueue----
            // 55
            // 33
            // 77
            // 66
            // ----peek----
            // 55
            // ----take----
            // 55
            // 33
            // 77
            // 66
            // null
        }
        ```

## 环状队列
[top](#catalog)
- 标记参数
    - maxSize，该队列的最大容量
    - front，表示队头
        - 随着数据输出而改变
        - 初始化为 0，**指向队列头元素**
    - rear，表示队尾的后一个位置
        - 随着数据输入而改变
        - 初始化为 0，**指向 `队尾元素 + 1` 的位置**
        - **会空出一个元素的空间**
- 实现方法
    - 根据 maxSize 创建数组
    - 队列中的有效数据个数：`(rear + maxSize - front) % maxSize`
    - 入队
        1. 检查队列是否已满
            - 如果 `(rear + 1) % maxSize == front`，则队列已满
        2. 入队时，先保存数据，然后 rear 指针往后移动: `rear+1`，保证能够空一个元素
    - 出队
        1. 检查队列是否为空
            - 当 `front == rear` 时，队列为空
        2. 出队时，front 指针往后移动: `front+1`，然后返回指向的元素

- 注意事项
    - 队列是否已满与 front 或 rear 的位置无关。只要 `front == rear`，即二者重叠时，队列为空
    - 因为会空出一个元素，所以队列的实际容量为 maxsize - 1
- 基本实现
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/queue/CycleArrayQueue.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/queue/CycleArrayQueue.java)
    - 代码内容
        ```java
        public class CycleArrayQueue<T> {
            private int maxSize;
            private int front;
            private int rear;
            private T[] container;

            public CycleArrayQueue(int maxSize) {
                this.maxSize = maxSize;
                front = 0;
                rear = 0;
                container = (T[])new Object[maxSize];
            }

            public boolean isFull(){
                return (rear + 1) % maxSize == front;
            }

            public boolean isEmpty(){
                return front == rear;
            }

            public void add(T elem){
                if (isFull()){return;}

                container[rear] = elem;

                // 需要取余，防止溢出
                rear = (rear + 1) % maxSize;
            }

            public T take(){
                if (isEmpty()){return null;}
                T elem = container[front];

                front = (front + 1) % maxSize;
                return elem;
            }

            public T peek(){
                if (isEmpty()){return null;}

                return container[front];
            }

            public void showQueue(){
                for (int i = front; i< front + size(); i++){
                    System.out.println(container[i % maxSize]);
                }
            }

            // 返回队列中的有效数据个数
            public int size(){
                return (rear + maxSize - front) % maxSize;
            }
        }
        ```

- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/queue/ArrayQueueTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/queue/ArrayQueueTest.java)

# 数据结构-单链表
## 单链表简介
[top](#catalog)
- 链表，Linked List
- 链表是有序的列表
- 链表的存储结构
    - 头结点部分
    
        |头指针|next域|
        |-|-|
        |HEAD|150|
    
    - 链表部分
    
        |地址|data域|next域|
        |-|-|-|
        |110|a1|180|
        |120||
        |130|a3|170|
        |140|a5|NULL|
        |150|a1|110|
        |160|||
        |170|a4|140|
        |180|a2|130|

- 链表的存储方式
    - 链表以节点的方式存储
    - 每个节点包含一个 data域、next域
    - next域 指向下一个结点
    - 链表的各个节点间不一定是顺序保存的
    - **链表分为：带头节点的链表、不带头节点的链表**
        - 是否需要头结点，需要根据需求来确定

- 单链表的缺点
    - 查找结点时的方向是单向的，只能从前到后查找
    - 结点不能自我删除，需要辅助结点记录目标结点的前一个结点

## 单链表的注意事项
[top](#catalog)
- 遍历时，**使用的结点 与 遍历的起点**
    1. 如果要使用当前结点的下一个结点，或者搜索目标结点的前一个结点
        - 使用的结点: `temp.next`
        - 遍历的起点: `temp = head`
    2. 使用的结点是本次遍历时的结点
        - 使用的结点: `temp`
        - 遍历的起点: `temp = head.next`

## 单链表的基本实现
[top](#catalog)
- 头结点 head
    - 不保存具体的数据
    - 只用于表示单链表的头部
    - next域 指向链表部分的起点
- 判断链表为空
    - `head.next == null`
- 链表的遍历
    - 通过一个辅助元素 `temp`，来遍历整个链表
    - 判断是否到达链表的最后一个元素
        - `temp.next == null`
    - 判断链表是否遍历完
        - `temp == null`
- 增删改查操作
    - 链表的添加操作
        1. 先创建一个 head 头节点，用于表示链表的头部
        2. 每次添加一个节点时，最后一个元素的next域指向新节点
    - 链表元素的更新
        - 遍历每一个结点，并与目标数据进行比较，直到找到目标元素
        - 遍历的起点: `temp = head.next`
        - 比较方式: 当前结点与目标数据进行比较，`temp.compareTo(data)`
    - 链表元素的删除
        1. 遍历链表，找到目标结点的前一个结点 `temp`
        2. 遍历的起点: `temp = head`
        3. 比较方式: 当前结点的下一个结点与目标数据进行比较，`temp.next.compareTo(data)`
        4. 重新构建链表关： `temp.next = temp.next.next`

- 示例
    - 实现代码
        - 参考代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/base/BaseSingleLinkedList.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/base/BaseSingleLinkedList.java)
        - 结点
            ```java
            // 定义LinkedNode，每个 LinkedNode 对象就是一个节点
            class LinkedNode<T extends Comparable> implements Comparable{
                T data;
                LinkedNode<T> next;
            
                public LinkedNode(T data) {
                    this.data = data;
                    next = null;
                }
            
                @Override
                public String toString() {
                    return data.toString();
                }
            
                @Override
                public int compareTo(Object o) {
                    return this.data.compareTo(o);
                }
            }
            ```
        - 链表
            ```java
            public class BaseSingleLinkedList<T extends Comparable> {
                // 初始化一个头结点，不保存具体数据
                private LinkedNode<T> head = new LinkedNode<>(null);
            
                // 添加结点
                public void add(T data){
                    LinkedNode<T> temp = head;
            
                    // 搜索当前链表中的最后一个结点
                    while (temp.next != null){ // 表示是否到达链表的最后一个元素
                        temp = temp.next;
                    }
            
                    temp.next = new LinkedNode<>(data);
                }
            
                // 更新结点
                public void update(T data){
                    // 进入第一个结点，
                    LinkedNode<T> temp = head.next;
                    
                    // 搜索目标结点，并直接替换数据
                    while (temp != null){ // 如果链表为空，将直接退出
                        if (temp.compareTo(data) == 0){
                            temp.data = data;
                            break;
                        }
                        temp = temp.next;
                    }
                }
            
                // 删除结点
                public void delete(T data){
                    //从头结点开始，搜索目标结点的前一个结点
                    LinkedNode<T> temp = head;
                    
                    while (temp.next != null){ // 如果链表为空，将直接退出
                        if (temp.next.compareTo(data) == 0){
                            // 重建构建链表关系
                            temp.next = temp.next.next;
                            break;
                        }
                        temp = temp.next;
                    }
                }
            
                // 遍历链表
                public void showList(){
                    // 判断链表是否为空
                    if (head.next == null){
                        System.out.println("null list");
                        return;
                    }
            
                    // 进入第一个结点
                    LinkedNode<T> temp = head.next;
                    while(temp != null){
                        // 执行遍历操作
                        System.out.println(temp);
            
                        // 移动指针
                        temp = temp.next;
                    }
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
        - 测试代码
            ```java
            @Test
            public void test01(){
                // 1. 测试添加和遍历
                BaseSingleLinkedList<Student> list = new BaseSingleLinkedList<>();
                list.add(new Student(1, "test01", 11));
                list.add(new Student(4, "test04", 14));
                list.add(new Student(2, "test02", 12));
        
                list.showList();
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=4, name='test04', age=14}
                // Student{num=2, name='test02', age=12}
        
                // 2. 测试修改结点
                list.update(new Student(4, "newName", 20));
                list.showList();
        
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=4, name='newName', age=20}
                // Student{num=2, name='test02', age=12}
        
                // 3. 测试删除结点
                list.delete(new Student(4, "newName", 20));
                list.showList();
        
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=2, name='test02', age=12}
            }
            ```

## 有序单链表的实现
[top](#catalog)
- 链表的添加操作
    1. 通过辅助结点 `temp`，首先找到新结点的前一个位置
    2. 重新设置链表的顺序
        1. 设置新结点与后结点的关系: `新结点.next = temp.next`
        2. 设置前结点与新结点的关系: `temp.next = 新结点`
- 其他操作与[单链表的基本实现](#单链表的基本实现)现相同
    - 链表的遍历
        - 通过一个辅助元素 `temp`，来遍历整个链表
        - 判断是否到达链表的最后一个元素
            - `temp.next == null`
        - 判断链表是否遍历完
            - `temp == null`
    - 链表元素的更新
        - 遍历每一个结点，并与目标数据进行比较，直到找到目标元素
        - 遍历的起点: `temp = head.next`
        - 比较方式: 当前结点与目标数据进行比较，`temp.compareTo(data)`
        
    - 链表元素的删除
        1. 遍历链表，找到目标结点的前一个结点 `temp`
        2. 遍历的起点: `temp = head`
        3. 比较方式: 当前结点的下一个结点与目标数据进行比较，`temp.next.compareTo(data)`
        4. 重新构建链表关： `temp.next = temp.next.next`

    
- 示例
    - 实现代码
        - 参考代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/base/OrderSingleLinkedList.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/base/OrderSingleLinkedList.java)
        - 结点
            ```java
            class OrderLinkedNode<T extends Comparable> implements Comparable{
                T data;
                OrderLinkedNode<T> next;
            
                public OrderLinkedNode(T data) {
                    this.data = data;
                    next = null;
                }
            
                @Override
                public int compareTo(Object o) {
                    return this.data.compareTo(o);
                }
            
                @Override
                public String toString() {
                    return data.toString();
                }
            }
            ```
        - 链表
            ```java
            public class OrderSingleLinkedList<T extends Comparable> {
                private OrderLinkedNode<T> head = new OrderLinkedNode<>(null);
                public void add(T data){
                    OrderLinkedNode<T> temp = head;
                    // 1. 搜索新结点的前一个结点
                    while(temp.next != null){ // 表示是否到达链表的最后一个元素
                        // 如果后结点比新结点大，则执行拆入。链表整体是升序排列
                        if (temp.next.compareTo(data) > 0){
                            break;
                        }
            
                        temp = temp.next;
                    }
            
                    // 2. 插入新结点
                    OrderLinkedNode<T> newNode = new OrderLinkedNode<>(data);
                    newNode.next = temp.next;
                    temp.next = newNode;
                }
            
                // 更新结点
                public void update(T data){
                    // 进入第一个结点，
                    OrderLinkedNode<T> temp = head.next;
            
                    // 搜索目标结点，并直接替换数据
                    while (temp != null){ // 如果链表为空，将直接退出
                        if (temp.compareTo(data) == 0){
                            temp.data = data;
                            break;
                        }
                        temp = temp.next;
                    }
                }
            
                // 删除结点
                public void delete(T data){
                    //从头结点开始，搜索目标结点的前一个结点
                    OrderLinkedNode<T> temp = head;
                    
                    while (temp.next != null){ // 如果链表为空，将直接退出
                        if (temp.next.compareTo(data) == 0){
                            // 重新构建链表关系
                            temp.next = temp.next.next;
                            break;
                        }
            
                        temp = temp.next;
                    }
                }
            
                public void showList(){
                    if (head.next == null){
                        System.out.println("null list");
                        return;
                    }
            
                    OrderLinkedNode<T> temp = head.next;
                    while(temp != null){
                        // 执行遍历操作
                        System.out.println(temp);
            
                        temp = temp.next;
                    }
                }
            }
            ```
    - 测试内容
        - 参考代码
            - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
        - 测试代码
            ```java
            @Test
            public void test02(){
                // 1. 测试添加和遍历
                OrderSingleLinkedList<Student> list = new OrderSingleLinkedList<>();
                list.add(new Student(1, "test01", 11));
                list.add(new Student(4, "test04", 14));
                list.add(new Student(2, "test02", 12));
        
                list.showList();
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=4, name='test04', age=14}
                // Student{num=2, name='test02', age=12}
        
                // 2. 测试修改结点
                list.update(new Student(4, "newName", 20));
                list.showList();
        
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=4, name='newName', age=20}
                // Student{num=2, name='test02', age=12}
        
                // 3. 测试删除结点
                list.delete(new Student(4, "newName", 20));
                list.showList();
        
                // 输出
                // Student{num=1, name='test01', age=11}
                // Student{num=2, name='test02', age=12}
            }
            ```

## 单链表常见问题
[top](#catalog)
### 单链表常见问题汇总
- 求单链表中有效结点的个数
- 查找单链表中的倒数第k个结点
- 单链表的反转
- 逆序打印单链表
    - 方式1：反向遍历
    - 方式2：stack栈
- 合并两个有序的单链表，合并之后仍然有序

### 求单链表中有效结点的个数
[top](#catalog)
- 实现方式
    - 从 `temp = head.next` 开始遍历，并记录遍历次数
    - 直到 `temp == null` 时，遍历结束，并返回结果
- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java)
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java)
    - 代码内容
        ```java
        // 获取单链表中有效结点的个数
        public int length(){
            LinkedNode<T> temp = head.next;
            int length = 0;
            while(temp != null){
                length++;
                temp = temp.next;
            }
            return length;
        }
        ``` 
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testLength(){
            BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
            list.add(111);
            list.add(222);
            list.add(333);
    
            assert (list.length() == 3);
    
            OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
            list2.add(111);
            list2.add(222);
            list2.add(333);
    
            assert (list2.length() == 3);
        }
        ```

### 查找单链表中的倒数第k个结点
[top](#catalog)
- 实现方式
    1. 方法接收一个index，index表示的是倒数第index结点
    2. 遍历链表获取链表中有效元素的个数: length
    3. 校验index的范围: `(0, length]`
    4. 计算到达目标元素需要的遍历次数: `n = length - index`
    5. 从 `head.next` 开始再遍历 `n` 次，获取目标元素
    6. 如果链表为空，或者index越界，则返回null
- 说明示例
    - 如链表有4个元素
    - 求倒数第 1 个元素，`length - index = 3`，需要从 `head.next` 开始遍历3次
    - 求倒数第 4 个元素，`length - index = 0`，直接返回 `head.next`，即遍历0次
- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java)
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java)
    - 代码内容
        ```java
        // 获取链表中倒数第k个结点
        public T findLastIndexNode(int index){
            // 1. 如果链表为空，则返回null
            if (head.next == null){ return null; }
    
            // 2. 遍历链表获取链表中有效元素的个数: length
            int length = this.length();
    
            // 3. 校验index的范围: `(0, length]`
            if (index <=0 || index > length){ return null; }
    
            // 4. 计算目标元素的位置: `length - index`
            // 5. 再次遍历，获取目标元素
            LinkedNode<T> temp = head.next;
            for(int i=0; i < length - index; i++){
                temp = temp.next;
            }
    
            return temp.data;
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
    - 测试代码
        ```java
        // 测试获取单链表的倒数第index个结点
        @Test
        public void testFindLastIndexNode(){
            BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
            list.add(111);
            list.add(222);
            list.add(333);
            list.add(444);
    
            assert(list.findLastIndexNode(1) == 444);
            assert(list.findLastIndexNode(4) == 111);
            assert(list.findLastIndexNode(0) == null);
    
            OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
            list2.add(111);
            list2.add(222);
            list2.add(333);
            list2.add(444);
    
            assert(list2.findLastIndexNode(1) == 444);
            assert(list2.findLastIndexNode(4) == 111);
            assert(list2.findLastIndexNode(0) == null);
        }
        ```
    

### 单链表的反转
[top](#catalog)
- 实现方式
    1. 如果链表为空、或链表只有一个元素时，则不需要执行反转
    2. 创建三个临时遍历
        - currentNode，保存当前的结点
        - nextNode，保存当前结点的下一个结点
            - 用于防止当前结点连接到反转链表后，丢失原始的后结点
        - reversedList，反转后的链表
            - 本身就是链表中的第一个有效元素，不是head结点
            - 初始化为null，相当于链表的结束
    3. 将 currentNode 定位到第一有效元素 `head.next`
    4. 遍历链表，并开始反转链表中的结点
        1. `nextNode = currentNode.next`
            - nextNode 保存 currentNode 的下一个结点
            - 防止重新设置链表顺序后，丢失原始的后结点
        2. `currentNode.next = reversedList`
            - currentNode 的后结点连接 reversedList 
        3. `reversedList = currentNode`
            - 将 reversedList 重新指向为 currentNode
            - 保证 reversedList 一直是反转后的一个元素
        4. `currentNode = nextNode`，将元素定位到下一个元素
        5. 重复执行 1- 4，直到 `currentNode == null，即遍历完成
    5. 将头结点指向反转后链表：`head.next = reversedList`
- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java)
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java)
    - 代码内容
        ```java
        public void reverse(){
            if (head.next == null || head.next.next == null){return;}

            // 1. 将 currentNode 定位到第一个有效结点
            LinkedNode<T> currentNode = head.next;
            LinkedNode<T> nextNode = null;
            LinkedNode<T> newList = null;
    
            while(currentNode != null){
                // 2. 保存当前结点的原始后结点
                nextNode = currentNode.next;
                // 3. 将当前元素的next连接到反转链表上
                currentNode.next = reversedList;
                // 4. 重新设定反转链表的指向
                reversedList = currentNode;
                // 5. 重新设置当前结点为原始的后结点
                currentNode = nextNode;
            }
    
            // 6. 将头结点的指向反转后的链表
            head.next = reversedList;
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testReverse(){
            BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
            list.add(111);
            list.add(222);
            list.add(333);
            list.add(444);
    
            list.showList();
            list.reverse();
            list.showList();
            // 输出
            // 444 、333 、222 、111
            System.out.println("---------------");
    
            OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
            list2.add(111);
            list2.add(222);
            list2.add(333);
            list2.add(444);
    
            list2.showList();
            list2.reverse();
            list2.showList();
            // 输出
            // 444 、333 、222 、111
        }
        ```
    
- 代码执行过程分析（等号表示复制，箭头表示next的指向）
    1. 初始化变量，将 `currentNode` 指向链表的第一个有效结点
        - ![step0](imgs/data_structure/linkedlist/reverse/step0.png)
    2. 遍历链表，并开始创建反转链表
        - ![step1](imgs/data_structure/linkedlist/reverse/step1.png)
        - ![step2](imgs/data_structure/linkedlist/reverse/step2.png)
        - ![step3](imgs/data_structure/linkedlist/reverse/step3.png)
        - ![step4](imgs/data_structure/linkedlist/reverse/step4.png)
    3. 遍历结束后，将头结点连接到反转链表
        - ![step5](imgs/data_structure/linkedlist/reverse/step5.png)

### 逆序打印单链表
[top](#catalog)
- 方式1：反向遍历
    - 将链表反转后，再进行遍历
    - 缺点
        - 会改变链表的结构，**不推荐使用**
- 方式2：stack栈
    1. 遍历链表，并将每个结点的引用保存到栈中
    2. 利用栈后进先出的特性，依次弹出栈中的元素，实现逆序遍历
    
- 方式2的实现
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/BaseSingleLinkedList02.java)
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java)
    - 代码内容
        ```java
        public void reverseShowList(){
            if (head.next == null){
                System.out.println("null list");
                return;
            }
    
            Stack<LinkedNode<T>> stack = new Stack<>();
    
            // 1. 遍历链表，并压入栈中
            LinkedNode<T> temp = head.next;
            while (temp != null){
                stack.push(temp);
                temp = temp.next;
            }
    
            // 2. 依次弹出栈中的元素，实现逆序遍历
            while (stack.size() > 0){
                System.out.println(stack.pop());
            }
        }
        ```

- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testReverseShowList(){
            BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
            list.add(111);
            list.add(222);
            list.add(333);
            list.add(444);
    
            System.out.println("-------逆序输出--------");
            list.reverseShowList();
            // 输出
            // 444 、333 、222 、111
            System.out.println("-------正常输出--------");
            list.showList();
    
            OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
            list2.add(111);
            list2.add(222);
            list2.add(333);
            list2.add(444);
    
            System.out.println("-------逆序输出--------");
            list2.reverseShowList();
            // 输出
            // 444 、333 、222 、111
            System.out.println("-------正常输出--------");
            list2.showList();
        }
        ```

### 合并两个有序的单链表
[top](#catalog)
- 实现方式
    1. 设置两个变量，分别定位到两个链表的第一个有效元素
        - `temp01 = head.next`
        - `temp02 = otherList.head.next`
    2. 设置一个变量指向当前链表的头结点: `newListNode = head`
    3. 遍历两个链表，并比较两个链表的结点的大小，按照顺序将目标结点，接入 newListNode 的结点
        - `newListNode.next = temp01/temp02`
    4. 遍历两个链表，直到其中一个链表遍历完
    5. 将未遍历完的链表的剩余部分添加到 `newListNode`
- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/single/forproblem/OrderSingleLinkedList02.java)
    - 代码内容
        ```java
        public void merge(OrderSingleLinkedList02<T> otherList){
            // 如果当前链表为空，则直接使用另外一个链表
            if (head.next == null){
                head = otherList.head;
                return ;
            }
    
            // 如果另一个链表是空，则停止合并
            if (otherList.head.next == null){
                return;
            }
    
            OrderLinkedNode<T> temp01 = head.next;
            OrderLinkedNode<T> temp02 = otherList.head.next;
            OrderLinkedNode<T> newListNode = head;
            while (temp01 != null && temp02 != null){
                // 两个列表的结点比较大小，将小的结点设置到 链表中，保持链表的升序特性
                if (temp01.compareTo(temp02.data) < 0 ){
                    newListNode.next = temp01;
                    newListNode = newListNode.next;
                    temp01 = temp01.next;
                } else {
                    newListNode.next = temp02;
                    newListNode = newListNode.next;
                    temp02 = temp02.next;
                }
            }
    
            // 将两个链表剩余的部分补充到新链表中
            if (temp01 != null){
                newListNode.next = temp01;
            } else if (temp02 != null) {
                newListNode.next = temp02;
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/SingleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testMerge(){
            OrderSingleLinkedList02<Integer> list = new OrderSingleLinkedList02<>();
            list.add(222);
            list.add(444);
    
            OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
            list2.add(111);
            list2.add(333);
            list2.add(666);
            
            list.merge(list2);
    
            list.showList();
            // 输出
            // 111 、222 、333 、444 、666
        }
        ```

# 数据结构-双向链表
## 双向链表简介
[top](#catalog)
- 双向链表包含两个引用: next、prev，分别指向当前元素的前一个结点和后一个结点
- 双向链表的优点
    - 查找结点时的方向是双向的，可以向前或这向后查找
    - 双向链表可以自我删除，不需要借助辅助结点
    
## 双向链表的基本实现
[top](#catalog)
- 双向链表的遍历
    - 遍历方式与单链表相同
    - 通过一个辅助元素 `temp`，来遍历整个链表
    - 判断是否到达链表的最后一个元素
        - `temp.next == null`
    - 判断链表是否遍历完
        - `temp == null`
- 双向链表的增删改查
    - 链表的添加操作（与单链表相同）
        1. 先创建一个 head 头节点，用于表示链表的头部
        2. 每次添加一个节点时，最后一个元素的next域指向新节点
        3. 新结点的prev域指向最后一个元素
    - 链表元素的更新（与单链表相同）
        - 遍历每一个结点，并与目标数据进行比较，直到找到目标元素
        - 遍历的起点: `temp = head.next`
        - 比较方式: 当前结点与目标数据进行比较，`temp.compareTo(data)`
    - 链表的删除
        1. 遍历链表，找到目标结点 `temp`
        2. 连接目标结点的前结点和后结点: 
            1. `temp.prev.next = temp.next`
            2. `temp.next.prev = temp.prev`
                - 如果当前temp结点是最后一个结点，则不执行，防止空指针异常
- 双向链表的实现
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/doublelinked/DoubleLinkedList.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/doublelinked/DoubleLinkedList.java)

    - 结点
        ```java
        class DoubleLinkedNode<T extends Comparable> implements Comparable{
            T data;
            DoubleLinkedNode<T> prev;
            DoubleLinkedNode<T> next;
        
            public DoubleLinkedNode(T data) {
                this.data = data;
                next = null;
                prev = null;
            }
        
            @Override
            public String toString() {
                return data.toString();
            }
        
            @Override
            public int compareTo(Object o) {
                return this.data.compareTo(o);
            }
        }
        ```
    - 双向链表
        ```java
        public class DoubleLinkedList<T extends Comparable> {
            private DoubleLinkedNode<T> head = new DoubleLinkedNode<>(null);
        
            // 添加元素
            public void add(T data){
                DoubleLinkedNode<T> temp = head;
        
                // 遍历链表，找到最后一个结点
                while (temp.next != null){
                    temp = temp.next;
                }
        
                // 将结点添加到链表末尾
                DoubleLinkedNode<T> node = new DoubleLinkedNode<>(data);
                temp.next = node;
                node.prev = temp;
            }
        
            // 遍历链表
            public void showList(){
                if (head.next == null) {
                    System.out.println("null list");
                    return;
                }
        
                DoubleLinkedNode<T> temp = head.next;
                while (temp != null){
                    System.out.println(temp);
                    temp = temp.next;
                }
            }
        
            // 更新元素
            public void update(T data){
                if (head.next == null){ return; }
                DoubleLinkedNode<T> temp = head.next;
        
                while (temp != null){
                    if (temp.compareTo(data) == 0){
                        temp.data = data;
                        break;
                    }
        
                    temp = temp.next;
                }
            }
        
            // 删除结点
            public void delete(T data){
                if (head.next == null){ return; }
        
                DoubleLinkedNode<T> temp = head.next;
                while (temp != null){
                    if (temp.compareTo(data) == 0){
                        // 连接目标结点的前结点和后结点
                        temp.prev.next = temp.next;
        
                        // 如果当前temp结点是最后一个结点，则不执行，防止空指针异常
                        if (temp.next != null){
                            temp.next.prev = temp.prev;
                        }
                        break;
                    }
        
                    temp = temp.next;
                }
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/DoubleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/DoubleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testShowList(){
            DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
            list.add(111);
            list.add(333);
            list.add(555);
    
            list.showList();
        }
    
        @Test
        public void testUpdate(){
            DoubleLinkedList<Student> list = new DoubleLinkedList<>();
            list.add(new Student(1, "test01", 11));
            list.add(new Student(4, "test04", 14));
            list.add(new Student(2, "test02", 12));
    
            // 修改结点
            list.update(new Student(4, "newName", 20));
            list.showList();
    
            // 输出
            // Student{num=1, name='test01', age=11}
            // Student{num=4, name='newName', age=20}
            // Student{num=2, name='test02', age=12}
        }
    
        @Test
        public void testDelete(){
            DoubleLinkedList<Student> list = new DoubleLinkedList<>();
            list.add(new Student(1, "test01", 11));
            list.add(new Student(4, "test04", 14));
            list.add(new Student(2, "test02", 12));
    
            System.out.println("------删除前------");
            list.showList();
    
            System.out.println("------删除后------");
            // 删除结点
            list.delete(new Student(2, "test04", 14));
            list.showList();
    
            // 输出
            // Student{num=1, name='test01', age=11}
            // Student{num=2, name='test02', age=12}
        }
        ```
      
## 有序双向链表的实现
[top](#catalog)
- 实现方式
    - 添加操作
        1. 从 head 结点开始遍历链表，搜索**目标结点的前一个结点 temp**
        2. 分别建立前结点: temp、后结点: temp.next 与 新结点之间的关系
            - 创建结点关系时，先创建新结点与后结点的关系
            - 如果先创建前结点与新结点的关系，会丢失原始后结点的引用
        3. 如果已经遍历到链表最后一个结点，`temp.next == null`，为了防止空指针异常，不能执行 `temp.next.prev = node`
    - 其他操作与普通的双向链表相同
- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/doublelinked/OrderDoubleLinkedList.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/doublelinked/OrderDoubleLinkedList.java)
    - 代码内容--添加元素部分
        ```java
        // 添加元素
        public void add(T data){
            OrderDoubleLinkedNode<T> temp = head;
    
            // 遍历链表，找到最后一个结点
            while (temp.next != null){
                // 如果后结点比新结点大，则将新结点添加到后结点前面，保持链表的升序特性
                if (temp.next.compareTo(data) > 0){
                    break;
                }
                temp = temp.next;
            }
    
            // 将结点添加到链表中
            OrderDoubleLinkedNode<T> node = new OrderDoubleLinkedNode<>(data);
    
            // 建立新结点与后结点的关系
            node.next = temp.next;
            if (temp.next != null){
                temp.next.prev = node;
            }
    
            // 建立前结点与新结点的关系
            temp.next = node;
            node.prev = temp;
        }
        ```

- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/OrderDoubleLinkedListTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/OrderDoubleLinkedListTest.java)
    - 测试代码
        ```java
        @Test
        public void testOrderDoubleLinkedList(){
            OrderDoubleLinkedList<Integer> list = new OrderDoubleLinkedList<>();
            list.add(222);
            list.add(111);
            list.add(444);
            list.add(333);
            
            list.showList();
            // 输出
            // 111
            // 222
            // 333
            // 444
        }
        ```

# 数据结构-单向环形链表
## 单向环形链表的实现
[top](#catalog)
- 实现方式
    1. 不使用head结点，使用变量 first、end 分别保存第一个结点和最后一个结点
        - 通过 end 来避免添加元素时的遍历操作
    2. 添加第一个结点时，将 first、end 同时设置为新结点，然后构成环状 `end.next = first`
    3. 后续添加新结点时，将新结点接到 `end.next`，然后将 end 移动到新结点，再重新构成环状 `end.next = first`
- 实现内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/cycle/CycleLinkedList.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/cycle/CycleLinkedList.java)
    - 结点
        ```java
        class LinkedNode<T extends Comparable> implements Comparable{
            T data;
            LinkedNode<T> next;
        
            public LinkedNode(T data) {
                this.data = data;
                next = null;
            }
        
            @Override
            public String toString() {
                return data.toString();
            }
        
            @Override
            public int compareTo(Object o) {
                return this.data.compareTo(o);
            }
        }
        ```
    - 环形链表
        ```java
        public class CycleLinkedList<T extends Comparable> {
            private LinkedNode<T> first;
            private LinkedNode<T> end;
            
            // getter
            // ...

            public void add(T data){
                LinkedNode<T> newNode = new LinkedNode<>(data);
                // 1. end连接newNode，然后重置end结点
                if (first == null){
                    first = newNode;
                    end = newNode;
                } else {
                    end.next = newNode;
                    end = newNode;
                }
        
                // 2. 重置 end 和 first 的关系
                end.next = first;
            }
        
            public void showList(){
                if (first == null){
                    System.out.println("null list");
                    return;
                }
        
                LinkedNode<T> temp = first;
                do{
                    System.out.println(temp);
                    temp = temp.next;
                }while(temp != first);
            }
        }
        ```

## 单向环形链表的应用--Josephu问题
[top](#catalog)
- Josephu问题
    1. 设编号为: 1, 2,... n 的n个人围坐在一圈
    2. 编号为k的人从 1 开始报数( 1<=k<=n )，数到m的那个人出列
    3. 从出列者的下一个人开始，从 1 报数，数到m的那个人再次出列
    4. 重复执行3，直到所有人出列
    5. 最终会产生一个出队编号的序列
- 实现方式
    1. 使用环形链表解决Josephu问题
    2. 创建一个有n个结点的环形链表，由第k个结点开始从 1 计数，计数到m时，将结点删除
    3. 重复2，直到链表为空
- 示例说明
    - ![Josephu_demo](imgs/data_structure/linkedlist/cyclelist/Josephu_demo.png)
- 实现内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/cycle/Josephu.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/linkedlist/cycle/Josephu.java)
    - 实现代码
        ```java
        public class Josephu {
            public static <T extends Comparable> void print(
                    CycleLinkedList<T> list, int start, int step){
                LinkedNode<T> temp = list.getEnd();
                // 检查链表是否为空
                if (temp == null){
                    System.out.println("null list");
                    return ;
                }
        
                // 检查参数
                if (start < 1){
                    System.out.println("error start");
                    return ;
                }
                if (step < 1){
                    System.out.println("error step");
                    return ;
                }
        
                // 从end开始，遍历到 start 结点的前结点
                for (int i=1; i<start; i++){
                    temp = temp.next;
                }
        
                // 从 start 的前结点开始遍历，直到链表中还剩一个结点 temp == temp.next
                while(temp != temp.next){
                    for (int i = 1; i < step; i++){
                        temp = temp.next;
                    }
        
                    // 输出结点
                    System.out.println(temp.next);
                    // 将结点删除
                    temp.next = temp.next.next;
                }
        
                // 输出最后一个结点
                System.out.println(temp);
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/JosephuTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/linkedlist/JosephuTest.java)
    - 测试代码
        ```java
        @Test
        public void test01(){
            CycleLinkedList<Integer> list = new CycleLinkedList<>();
            list.add(11);
            list.add(22);
            list.add(33);
            list.add(44);
            list.add(55);
    
            Josephu.print(list, 1, 2);
            // 输出
            // 22 、44 、11 、55 、33
        }
        ```

# 数据结构-栈
## 栈简介
[top](#catalog)
- 栈 stack
- 栈是一个**先入后出**的**有序列表**（FILO，First In Last Out）
- 栈是一种特殊的线性表
    - 它限制的元素的插入和删除只能在线性表的一端进行
    - **栈顶**: 允许插入和删除的一端
    - **栈底**: 固定的一端
- 栈的应用场景
    - 子程序的调用
        - 在指向子程序前，将下个指令的地址存到栈中
        - 子程序执行完后再将地址取出，回到原来的程序中
    - 处理递归调用
        - 和子程序调用类似，除了存储下一个指令的地址外，还需要保存参数、变量的数据
    - 表达式的转换与求值
        - 中缀表达式转后缀表达式
    - 二叉树的遍历
    - 图结构的深度优先搜索算法（depth--first）

## 使用数组实现栈
[top](#catalog)
- 实现方式
    - 定义一个 top 表示栈顶，初始化为 -1
    - 入栈
        - 需要判断数组是以满: `top == maxSize - 1`
        - 移动栈顶: `top++`
        - 添加元素`stack[top] = data`
    - 出栈
        - 需要判断栈是否为空: `top == -1`
        - 将栈顶元素保存到临时变量中: `temp = stack[top]`
        - 移动栈顶: `top--`
        - 返回保存的栈顶元素

- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/arraystack/ArrayStack.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/arraystack/ArrayStack.java)
    - 代码内容
        ```java
        public class ArrayStack<T> {
            private int maxSize;
            private T[] stack;
            private int top = -1;
        
            public ArrayStack(int maxSize) {
                this.maxSize = maxSize;
                stack = (T[])new Object[maxSize];
            }
        
            // 检查栈是否已满
            public boolean isFull(){
                return top + 1 == maxSize;
            }
        
            // 检查栈是否为空
            public boolean isEmpty(){
                return top == -1;
            }
        
            // 入栈
            public void push(T data){
                if (isFull()){
                    System.out.println("full stack");
                    return ;
                }
        
                top++;
                stack[top] = data;
            }
        
            // 出栈
            public T pop(){
                if (isEmpty()){
                    System.out.println("empty stack");
                    return null;
                }
        
                T temp = stack[top];
                top--;
                return temp;
            }
        
            // 从栈顶开始遍历栈
            public void showStack(){
                if (isEmpty()){
                    System.out.println("empty stack");
                    return;
                }
        
                for (int i=top; i>= 0; i--){
                    System.out.println(stack[i]);
                }
            }
        }
        ```
- 测试代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/ArrayStackTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/ArrayStackTest.java)
    - 测试内容
        ```java
        @Test
        public void test01(){
            ArrayStack<Integer> stack = new ArrayStack<>(3);
            assert (stack.pop() == null); // 输出: empty stack 
    
            stack.push(111);
            stack.push(222);
            stack.push(333);
            stack.push(444); // 输出: full stack 
    
            stack.showStack();
            // 输出: 
            // 333
            // 222
            // 111
        }
        ```

## 使用单链表实现栈
[top](#catalog)
- 实现方式
    - 创建一个没有head的链表，用first标识第一个有效结点
        - first 初始化为 null，**表示当前栈为空**
    - 链表的 first元素 表示**栈顶**
    - 链表的最后一个元素表示**栈底**
    - 入栈
        1. 将 first 设置为新结点的后结点
        2. 将 first 重置为新结点
    - 出栈
        1. 将 first 结点保存到临时遍历中
        2. 将 first 后移 `first = first.next`
        3. 返回临时变量中的数据
    - 链表实现与数组不同的地方
        - 链表没有最大的范围，可以一直添加
        - 链表只需要在 出栈 时，判断栈是否为空即可
        - 入栈时，不需要判断栈是否已满
    - 判断栈为空
        - `first == null`

- 实现代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/linkedstack/LinkedListStack.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/linkedstack/LinkedListStack.java)
    - 代码内容
        ```java
        public class LinkedListStack<T> {
            private LinkedNode<T> first;
        
            public boolean isEmpty(){
                return first == null;
            }
        
            public void push(T data){
                LinkedNode<T> newNode = new LinkedNode<T>(data);
                // 1. 将 first 设置为新结点的后结点
                newNode.next = first;
                // 2. 将 first 重置为新结点
                first = newNode;
            }
        
            public T pop(){
                if (isEmpty()){
                    System.out.println("empty stack");
                    return null;
                }
        
                // 获取第一个结点，然后将 first 结点向后移动
                LinkedNode<T> temp = first;
                first = first.next;
        
                return temp.data;
            }
        
            // 从栈顶开始遍历栈
            public void showStack(){
                if (isEmpty()){
                    System.out.println("empty stack");
                    return ;
                }
        
                LinkedNode<T> temp = first;
                while (temp != null){
                    System.out.println(temp);
                    temp = temp.next;
                }
            }

            // 查看栈顶的元素
            public T peek(){
                if (isEmpty()){ return null; }
                return first.data;
            }
        }
        ```
- 测试代码
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/LinkedListStackTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/LinkedListStackTest.java)
    - 测试内容
        ```java
        @Test
        public void test01(){
            LinkedListStack<Integer> stack = new LinkedListStack<>();
            assert (stack.pop() == null); // 输出: empty stack
    
            stack.showStack(); // 输出: empty stack
    
            stack.push(111);
            stack.push(222);
            stack.push(333);
            stack.push(444);
    
            stack.showStack();
            // 输出:
            // 444
            // 333
            // 222
            // 111
        }
        ```

# 栈的应用--前缀中缀后缀表达式
## 前缀中缀后缀表达式简介
[top](#catalog)
- 前缀表达式
    - 前缀表达式又称**波兰表达式**
    - 运算符位在操作数之前
    - 说明示例
        - 原始公式:  `(3 + 4) x 5 - 6`
        - 前缀表达式:`- x + 3 4 5 6`
    - 求解过程
        1. **从右向左**扫描表达式
        2. 遇到数字时，将数字压入栈
        3. 遇到运算符时，弹出栈顶的两个数，进行运算。再将结果入栈
        4. 重复上述过程直到最左边，得到的结果即为表达式的结果

- 中缀表达式
    - 中缀表达式就是常见的运算表达式，如`(3 + 4) x 5 - 6`
    - 中缀表达式对于计算机不好操作，需要判断运算符的优先级
    - 计算时，会将中缀表达式转换为其他表达式，一般转成**后缀表达式**

- 后缀表达式
    - 后缀表达式又称**逆波兰表达式**
    - 运算符位在操作数之后
    - 说明示例
        - 原始公式:  `(3 + 4) x 5 - 6`
        - 前缀表达式:`3 4 + 5 x 6 -`
    - 求解过程
        1. **从左向右**扫描表达式
        2. 遇到数字，压入栈
        3. 遇到运算符，弹出栈顶的两个数，进行运算。再将结果入栈
        4. 重复上述过程直到最右边，栈中只有一个数，即为表达式的结果

## 中缀表达式应用--中缀表达式计算器
### 中缀表达式计算器--实现方式1--存在减号问题
[top](#catalog)
- 需求
    - 输入一个表达式，如: `3 - 1 + 2 * 3 - 4 / 2`
    - 自动计算表达式的结果
- 实现方式1--不考虑减号问题
    1. 创建两个栈
        - s1 符号栈: 保存表达式中的运算符
        - s2 数栈: 保存表达式中的值、计算过程中产生的中间值
    2. 通过索引遍历表达式字符串
        1. 如果是数字，就放入 s2
        2. 如果是一个运算符
            - 如果 s1 为空，直接压入 s1
            - 如果 s1 不为空，与栈顶符号比较优先级
                - `当前符号 <= 栈顶符号`
                    - 从 s2 中pop两个数值，从 s2 弹出一个运算符，进行计算
                    - 计算时，**s2 的栈顶是右值，下一个元素是左值**
                    - 将计算结果压入 s1 
                    - 将当前符号压入 s2
                - `当前符号 > 栈顶符号`
                    - 将当前符号直接放入 s1
                    - 这表示：当前符号优先级过高，**需要等待当前运算符号的右值入栈**之后再计算
                        - 即通过与下一个符号的优先级比较结果来判断是否计算
    3. 表达式遍历完后
        1. 从 s2 弹出两个数，从 s1 弹出一个运算符，进行运算
        2. 运算后，将结果再次保存到 s2 的栈顶
        3. 重复上述操作， 直到 s1 为空
    4. 最终，s1 为空，s2 中只有一个元素，即表达式的计算结果

- 实现内容
    - 参考代码
        - 通用工具
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java)
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java)
        - 实现代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/InfixCalculator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/InfixCalculator.java)
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/InfixCalculatorTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/InfixCalculatorTest.java)

### 中缀表达式计算器的求解示例
[top](#catalog)
1. 遍历表达式
    - ![step1_analyze_expression](imgs/data_structure/stack/calculator/step1_analyze_expression.png)
2. 计算栈中的数据
    - ![step2_calculate_stack_data](imgs/data_structure/stack/calculator/step2_calculate_stack_data.png)

### 中缀表达式计算器--实现方式2--解决减号问题
[top](#catalog)
- 实现方式1中的**减号问题**
    - 减号问题的示例：`1 - 5 * 6 + 1`
        - 遍历之后，栈中的数据
            ```
            1
            30   +
            1    -
            数栈  符号栈
            ```
        - 如果按照之前的算法，计算过程为：`30 + 1 = 31; 1 - 31 = -30`
        - 这种情况下的计算结果是错误的，正确的计算过程为：`1 - 30 = -29; -29 + 1 = -28`
    - 出现减号问题的情况
        - 当符号栈的**非栈顶**结点是减号时，会出现计算顺序异常的问题
    - 出现减号问题的原因
        - 如执行: `30 + 1 = 31` 时，按照正常顺序计算时，包含减号时，应该考虑结合律，转化为: `1 - (30 - 1)` 来计算
        - 但是方案1中没有这种策略，同时这种策略在应用时也比较复杂，要查看栈中的多个元素
    - 减号问题的解决方案
        - 解决思路
            - **将减法转化为加法**
        - 操作方法
            - 将减号转为加号，将减号自身作为**下一个**数字的符号
            - 这样在计算时，减号操作已经包含在数值中，不会出现异常

- **实现方案2--解决减号问题**
    1. 初始化变量
        - 创建两个栈
            - s1 符号栈: 保存表达式中的运算符
            - s2 数栈: 保存表达式中的值、计算过程中产生的中间值
        - 创建一个符号保存参数: `sign`，初始化为 1
            - `sign = 1`，表示前一个符号不是减号
            - `sign = -1`，表示前一个符号是减号
    2. 通过索引遍历表达式字符串
        1. 如果是数字
            1. 执行 `sign * 数字`，为数字设置符号，即将减号操作移动到数值本身
            2. 保存到 s2
            3. 重置 `sign = 1`
        2. 如果是一个运算符
            - <label style='color:red'>解决减号问题</label>
                - 如果是减号，则将减号变为加号，然后设置 `sign = -1`
                - sign 将会在数值入栈时使用
            - 如果 s1 为空，直接压入 s1
            - 如果 s1 不为空，与栈顶符号比较优先级
                - `当前符号 <= 栈顶符号`
                    - 从 s2 中pop两个数值，从 s2 弹出一个运算符，进行计算
                    - 计算时，**s2 的栈顶是右值，下一个元素是左值**
                    - 将计算结果压入 s1 
                    - 将当前符号压入 s2
                - `当前符号 > 栈顶符号`
                    - 将当前符号直接放入 s1
                    - 这表示：当前符号优先级过高，**需要等待当前运算符号的右值入栈**之后再计算
                        - 即通过与下一个符号的优先级比较结果来判断是否计算
    3. 表达式遍历完后
        1. 从 s2 弹出两个数，从 s1 弹出一个运算符，进行运算
        2. 运算后，将结果再次保存到 s2 的栈顶
        3. 重复上述操作， 直到 s1 为空
    4. 最终，s1 为空，s2 中只有一个元素，即表达式的计算结果

- 实现内容
    - 参考代码
        - 通用工具
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java)
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java)
        - 实现代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/InfixCalculator2.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/InfixCalculator2.java)
    - 实现代码
        ```java
        public class InfixCalculator2 {
            public static int interpret(String expression) throws InvocationTargetException, IllegalAccessException {
                // 1. 使用单链表实现的栈来创建
                // 创建两个栈: 数栈、符号栈
                Stack<Integer> numStack = new Stack<>();
                Stack<Character> operatorStack = new Stack<>();
        
                // 2. 通过索引遍历表达式字符串
                char[] expressionChars = expression.toCharArray();
                int rightValue, leftValue, result;
                char c;
                String num = ""; // 多位数时临时保存高位数
                int sign = 1; // 如果是减号，则将sign设置为 -1，设置到下一个数字中，然后将减号转换为加号，并重置为 1
        
                for (int i = 0; i < expressionChars.length; i++) {
                    c = expressionChars[i];
                    // 如果是空格则跳过
                    if ( CalculatorUtils.isSpace(c) ){ continue; }
        
                    if ( CalculatorUtils.isNum(c) ){
                        num += c;
                        // 处理多位数
                        // 如果 已经到达最后一个字符，则直接入栈，或者下一位不是数值
                        if ( i == expressionChars.length - 1 || !CalculatorUtils.isNum(expressionChars[i + 1])){
                            // 保存数值的同时，设置符号
                            numStack.push(Integer.parseInt(num) * sign);
                            // 将符号重置为 1
                            sign = 1;
                            // 清空多位数的缓存
                            num = "";
                        }
                    } else if ( CalculatorUtils.isOperator(c) ){
                        // 如果是符号，需要判断当前符号与栈顶符号的优先级
        
                        // 如果是减号，则将sign置为 -1，并转换为 加号
                        if (c == '-'){
                            sign = -1;
                            c = '+';
                        }
        
                        // 如果符号栈为空，直接入栈
                        if (operatorStack.isEmpty()){
                            operatorStack.push(c);
                        } else {
                            // 如果符号栈不为空，需要比较操作符的优先级
                            // 当前符号 <= 栈顶符号
                            if ( CalculatorUtils.compareOperatorLevel(c, operatorStack.peek()) <= 0 ){
                                // 从数栈中pop两个数值
                                // 栈顶元素是右值，下一个元素是左值
                                rightValue = numStack.pop();
                                leftValue = numStack.pop();
        
                                // 从符号栈pop一个运算符，进行计算
                                result = CalculatorUtils.calculate(operatorStack.pop(), leftValue, rightValue);
                                // 将计算结果放入数栈
                                numStack.push(result);
        
                                // 将当前符号放入符号栈
                                operatorStack.push(c);
                            } else {
                                // 当前符号 > 栈顶符号
                                // 将当前符号直接放入符号栈
                                operatorStack.push(c);
                            }
                        }
                    } else {
                        throw new RuntimeException("未知字符: " + c);
                    }
                }
        
                // 3. 表达式遍历完后，顺序的从数栈和符号栈中pop出数据进行计算，**直到符号栈为空**
                while ( !operatorStack.isEmpty() ){
                    // 从数栈中pop两个数值
                    // 栈顶元素是右值，下一个元素是左值
                    rightValue = numStack.pop();
                    leftValue = numStack.pop();
        
                    // 从符号栈pop一个运算符，进行计算
                    result = CalculatorUtils.calculate(operatorStack.pop(), leftValue, rightValue);
                    // 将计算结果放入数栈
                    numStack.push(result);
                }
        
                // 4. 最终，符号栈为空，数栈中只有一个元素，即表达式的计算结果
                return numStack.pop();
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/InfixCalculator2Test.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/InfixCalculator2Test.java)
    - 测试代码
        ```java
        // 基本示例测试
        @Test
        public void testCalculator() throws InvocationTargetException, IllegalAccessException {
            int result = InfixCalculator2.interpret("3 - 1  + 2 * 3 - 4 / 2");
            System.out.println(result);
            // 输出: 6
        }
    
        // 多位数测试
        @Test
        public void testCalculator02() throws InvocationTargetException, IllegalAccessException {
            int result = InfixCalculator2.interpret("30 - 1  + 2 * 3 - 4 / 2");
            System.out.println(result);
            // 输出: 6
        }
    
        // 异常字符测试
        @Test
        public void testErrorChar() throws InvocationTargetException, IllegalAccessException {
            int result = InfixCalculator2.interpret("3 - 1 + a + 2 * 3 - 4 / 2");
            System.out.println(result);
            // 产生异常
            // java.lang.RuntimeException: 未知字符: a
        }
    
        // 单数字测试
        @Test
        public void testOneNumber() throws InvocationTargetException, IllegalAccessException {
            int result = InfixCalculator2.interpret("3");
            System.out.println(result);
            // 输出: 3
        }
    
        // 减号问题测试
        @Test
        public void testMinus01() throws InvocationTargetException, IllegalAccessException {
            int result = InfixCalculator2.interpret("1 - 5 * 6 + 1");
            // 表达式遍历结束后的栈
            // 1
            // 30 -
            // 1  -
    
            System.out.println(result);
            // 输出: -28
        }
        ```

## 后缀表达式应用--后缀表达式计算器
### 中缀表达式转后缀表达式的方法
[top](#catalog)
- 变换示例
    - 中缀表达式: `1 + ((2 + 3) X 4) - 5`
    - 后缀表达式: `1 2 3 + 4 X + 5 -`
- 实现步骤
    1. 初始化两个栈
        - s1 符号栈
        - s2 中间结果栈
    2. 从左向右扫描中缀表达式
    3. 遇到数值时，将其压入 s2
    4. 遇到运算符时
        1. 如果 s1 为空，或栈顶运算符为 `(`，则压入 s1
        2. 如果 s1 不为空，与 s1 栈顶比较优先级
            - `当前运算符 > s1 栈顶` 时，将当前运算符压入 s1
            - `当前运算符 <= s1 栈顶` 时，
                - 将 s1 栈顶的运算符弹出，压入 s2
                - 跳转到 4-1，继续检查当前运算符
    5. 遇到括号时
        - 如果是左括号 `(`，压入 s1
        - 如果是右括号 `)`，依次弹出 s1 栈顶，并压入 s2，直到s1弹出的是 `(`
            - 右括号 `)`，和弹出的左括号 `(`，不需要压入 s2
    6. 重复 2至5，直到表达式结束
    7. 将 s1 中剩余的运算符依次弹出，并压入 s2
    8. 依次弹出 s2 中的元素，并输出，**结果的逆序**即为后缀表达式
- 注意事项
    - s2 中间结果栈 的问题
        - 在整个转化过程中，没有出栈操作
        - 最后需要逆序输出
    - 所以 s2 一般会**使用数组代替**
        - 入栈时，将数据添加到数组末尾
        - 使用时，从前向后遍历

- 转化说明示例: `1 + ((2 + 3) * 4) - 5`

    |公式|当前字符|s2栈|s1栈|
    |-:|-:|:-|:-|
    |`1 + ((2 + 3) * 4) - 5`|` `|`                 `|`       `|
    |`  + ((2 + 3) * 4) - 5`|`1`|`1                `|`       `|
    |`    ((2 + 3) * 4) - 5`|`+`|`1                `|`+      `|
    |`     (2 + 3) * 4) - 5`|`(`|`1                `|`+ (    `|
    |`      2 + 3) * 4) - 5`|`(`|`1                `|`+ ( (  `|
    |`        + 3) * 4) - 5`|`2`|`1 2              `|`+ ( (  `|
    |`          3) * 4) - 5`|`+`|`1 2              `|`+ ( ( +`|
    |`           ) * 4) - 5`|`3`|`1 2 3            `|`+ ( ( +`|
    |`             * 4) - 5`|`)`|`1 2 3 +          `|`+ (    `|
    |`               4) - 5`|`*`|`1 2 3 +          `|`+ ( *  `|
    |`                ) - 5`|`4`|`1 2 3 + 4        `|`+ ( *  `|
    |`                  - 5`|`)`|`1 2 3 + 4 *      `|`+      `|
    |`                    5`|`-`|`1 2 3 + 4 * +    `|`-      `|
    |`                     `|`5`|`1 2 3 + 4 * + 5  `|`-      `|
    |`                     `|` `|`1 2 3 + 4 * + 5 -`|`       `|

     
### 中缀表达式转后缀表达式的实现   
[top](#catalog)
- 实现内容
    - 参考代码
        - 通用工具
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java)
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java)
        - 实现代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/SuffixCalculator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/SuffixCalculator.java)
    - 实现代码
        ```java
        public class SuffixCalculator {
            public static List<String> toSuffixExpression(String expr){
                // 1. 初始化两个栈
                // s1 符号栈
                Stack<String> s1 = new Stack<>();
                // s2 中间结果栈
                List<String> s2 = new LinkedList<>();
        
                // 2. 从左向右扫描中缀表达式
                char[] exprChars = expr.toCharArray();
                String num = "";
                char c;
                for (int i = 0; i < exprChars.length; i++) {
                    c = exprChars[i];
                    if ( c == ' '){ continue; }
        
                    if (CalculatorUtils.isNum(c)){
                        // 3. 遇到数值时，将其压入 s2
                        num += c;
                        if (i == exprChars.length - 1 || !CalculatorUtils.isNum(exprChars[i+1])){
                            s2.add(num);
                            num = "";
                        }
                    } else if (CalculatorUtils.isOperator(c)) {
                        // 4. 遇到运算符时
                        while (true) {
                            if (s1.isEmpty() || "(".equals(s1.peek())) {
                                // 4.1. 如果 s1 为空，或栈顶运算符为 `(`，则压入 s1
                                s1.push(String.valueOf(c));
                                break;
                            } else {
                                // 4.2. 如果 s1 不为空，与 s1 栈顶比较优先级
                                if (CalculatorUtils.compareOperatorLevel(c, s1.peek()) > 0) {
                                    // 4.2.1 `当前运算符 > s1 栈顶` 时，将当前运算符压入 s1
                                    s1.push(String.valueOf(c));
                                    break;
                                } else {
                                    // 4.2.2 `当前运算符 <= s1 栈顶` 时，
                                    // 4.2.2.1 将 s1 栈顶的运算符弹出，压入 s2
                                    s2.add(s1.pop());
                                    // 4.2.2.2 跳转到 4-1，继续检查当前运算符
                                }
                            }
                        }
                    } else if (CalculatorUtils.isBrackets(c)){
                        // 5. 遇到括号时
                        // 5.1 如果是 `(`，压入 s1
                        if (c == '('){
                            s1.push(String.valueOf(c));
                        } else {
                            // 5.2 如果是 `)`，依次弹出 s1 栈顶，并压入 s2，直到s1弹出的是 `(`
                            String operator;
                            while ( !"(".equals(operator = s1.pop()) ){
                                s2.add(operator);
                            }
                        }
                    } else {
                        throw new RuntimeException("未知字符: " + c);
                    }
                }
                // 6. 重复 2至5，直到表达式结束
        
                // 7. 将 s1 中剩余的运算符依次弹出，并压入 s2
                while (!s1.isEmpty()){
                    s2.add(s1.pop());
                }
        
                // 8. 将中间结果栈作为结果返回
                return s2;
            }
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/SuffixCalculatorTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/SuffixCalculatorTest.java)
    - 测试代码
        ```java
        // 基本测试
        @Test
        public void testToSuffixExpression(){
            List<String> list = SuffixCalculator.toSuffixExpression("1 + ((2 + 3) * 4) - 5");
            String[] result = {"1", "2", "3", "+", "4", "*", "+", "5", "-"};
    
            assert (list.toString().equals(Arrays.toString(result)));
        }
    
        // 多数字测试
        @Test
        public void testToSuffixExpressionMultiNum(){
            List<String> list = SuffixCalculator.toSuffixExpression("10 + ((21 + 3) * 4) - 5");
            String[] result = {"10", "21", "3", "+", "4", "*", "+", "5", "-"};
    
            assert (list.toString().equals(Arrays.toString(result)));
        }
        ```

### 后缀表达式计算器的实现
[top](#catalog)
- 实现方式
    - 接收一个普通的表达式作为参数，即接收一个中缀表达式 
    - 将中缀表达式转化为后缀表达式，如
        - `1 + ((2 + 3) * 4) - 5`
        - `1 2 3 + 4 * + 5 -`
    - 遍历后缀表达式
        1. 创建一个数值栈
        2. 如果是数字，则压入栈
        3. 如果是符号，从栈中弹出两个数进行运算
            - 栈顶是右值，栈顶的下一个元素是左值
        4. 重复 2 到 3，直到表达式扫描完
        5. 数值栈中还剩一个数，即计算结果

- 实现内容
    - 参考代码
        - 通用工具
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/Operator.java)
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/CalculatorUtils.java)
        - 实现代码
            - [/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/SuffixCalculator.java](/algorithm/src/java-algorithm/datastructure/src/main/java/com/ljs/learn/datastructure/stack/usage/calculator/SuffixCalculator.java)
    - 实现代码
        ```java
        public class SuffixCalculator {
            public static int interpret(String expression) throws InvocationTargetException, IllegalAccessException {
                return calculateSuffixExpr(toSuffixExpression(expression));
            }
        
            public static int calculateSuffixExpr(List<String> expr) throws InvocationTargetException, IllegalAccessException {
                // 1. 从左向右扫描表达式
                Stack<Integer> numStack = new Stack<>();
                int leftValue,rightValue, result;
                for (String node : expr) {
                    if (node.matches("\\d+")){
                        // 2. 遇到数字，压入栈
                        numStack.push(Integer.parseInt(node));
                    } else if (CalculatorUtils.isOperator(node)){
                        // 3. 遇到运算符，弹出栈顶的两个数，进行运算。再将结果入栈
                        rightValue = numStack.pop();
                        leftValue = numStack.pop();
                        result = CalculatorUtils.calculate(node, leftValue, rightValue);
                        numStack.push(result);
                    } else {
                        throw new RuntimeException("未知字符: " + node);
                    }
                }
                // 4. 重复上述过程直到最右边，栈中只有一个数，即为表达式的结果
                return numStack.pop();
            }
            // ...
        }
        ```
- 测试内容
    - 参考代码
        - [/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/SuffixCalculatorTest.java](/algorithm/src/java-algorithm/datastructure/src/test/java/com/ljs/learn/datastructure/stack/usage/SuffixCalculatorTest.java)
    - 测试代码
        ```java
        // 计算测试
        @Test
        public void testInterpret() throws InvocationTargetException, IllegalAccessException {
            int result = SuffixCalculator.interpret("1 + ((2 + 3) * 4) - 5");
            assert (result == 16);
        }
    
        // 多数字计算测试
        @Test
        public void testInterpretMultiNum() throws InvocationTargetException, IllegalAccessException {
            int result = SuffixCalculator.interpret("10 + ((21 + 3) * 4) - 5");
            assert (result == 101);
        }
        ```