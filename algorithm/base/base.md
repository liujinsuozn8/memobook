
部分内容整理自：《漫画算法：小灰的算法之路》

<span id="catalog"></span>
- [概述](#概述)
    - [T(n)函数](#t(n)函数)
    - [渐进时间复杂度](#渐进时间复杂度)
    - [空间复杂度](#空间复杂度)
    - [时间与空间的取舍](#时间与空间的取舍)
- [数据结构基础](#数据结构基础)
    - [数组](#数组)
    - [链表](#链表)
    - [数组与链表](#数组与链表)
    - [栈和队列](#栈和队列)
        - [栈stack](#栈stack)
        - [队列queue](#队列queue)
        - [双端队列](#双端队列)
    - [散列表hashTable](#散列表hashtable)
- [树](#树)
    - [二叉树](#二叉树)
    - [二叉树的应用](#二叉树的应用)
        - [深度优先遍历](#深度优先遍历)
            - [使用链表进行递归](#使用链表进行递归)
            - [使用栈来实现](#使用栈来实现)
        - [广度优先遍历](#广度优先遍历)
            - [层序遍历](#层序遍历)
    - [二叉堆](#二叉堆)
    - [优先队列](#优先队列)
- [排序算法](#排序算法)
    - [冒泡排序](#冒泡排序)
    - [快速排序](#快速排序)

# 概述
## T(n)函数
[top](#catalog)
* `T(n)`为程序基本执行次数的函数
* 也可以认为是程序的**相对执行时间函数**
* `n`为输入规模
* 4种常见的形式
    * 线性`T(n)=3n`
        ```java
        void method(int n){
            for(int i=0; i<n; i++){
                System.out.println(n);
                System.out.println(n);
                System.out.println(n);
            }
        }
        ```
    * 对数`T(n)=5log(n)`
        * 5表示输出次数，`log(n)`表示整体次数
        ```java
        void method(int n){
            for(int i=n; i<1; i >> 1){
                System.out.println(n);
                System.out.println(n);
                System.out.println(n);
                System.out.println(n);
                System.out.println(n);
            }
        }
        ```
    * 常数`T(n)=3`
        ```java
        void method(int n){
            System.out.println(n);
            System.out.println(n);
            System.out.println(n);
        }
        ```
    * 多项式`T(n)=0.5n^2 + 0.5n`
        ```java
        void method(int n){
            for (int i=0; i<n; i++){
                for (int j=0; j<i; j++){
                    System.out.println(j);
                }
                System.out.println(n);
            }
        }
        ```
## 渐进时间复杂度
[top](#catalog)
* 概念：若存在函数`f(n)`，使得当n趋近于无穷大时，`T(n)/f(n)`的极限值为**不等于0的常数**，则称`f(n)`是`T(n)`的**同数量级函数**，记作：`T(n)=O(f(n))`，`O`为算法的渐进时间复杂度，简称**时间复杂度**
    * 即把`T(n)`简化为一个数量级，数量级可以是：`n, n^2, n^3`等等
* **推导**时间复杂度的**几个原则**
    * `常数=1`：如果运行时间是数量级，则用常数1表示
    * `高阶的影响力`:只保留时间函数中的最高阶项
    * `忽略系数`：如果最高阶项存在，则省略最高阶项前面的系数
* 4中`T(n)`常见形式的时间复杂度
    * `T(n)=3n` ==> `T(n)=O(n)`，即把`3n`视作数量级`n`
        * 没有常数，则`3n ==> 3n`
        * 保留最高阶项，则`3n ==> 3n`
        * 忽略系数，则`3n ==> n`
    * `T(n)=5log(n)` ==> `T(n)=O(logn)`
        * 没有常数，则`5log(n) ==> 5log(n)`
        * 保留最高阶项，则`5log(n) ==> 5log(n)`
        * 忽略系数，则`log(n) ==> log(n)`
    * `T(n)=3` ==> `T(n)=O(1)`
        * 常数变为1:`3 ==> 1`
    * `T(n)=0.5n^2 + 0.5n` ==> `T(n)=O(n^2)`
        * 没有常数，则`0.5n^2 + 0.5n ==> 0.5n^2 + 0.5n`
        * 保留最高阶项，则`0.5n^2 + 0.5n ==> 0.5n^2`
        * 忽略系数，则`0.5n^2 ==> n^2`
* 4中常见形式的时间长短
    * `O(1)<O(logn)<O(n)<O(n^2)`

## 空间复杂度
[top](#catalog)
* 描述一个算法在执行过程中临时占用的内存空间大小
* 计算公式`S(n)=O(f(n))`
    * `n`为问题规模
    * `f(n)`为算法占用存储空间的函数
* 4种形式
    * 常量空间
        * 算法的存储空间大小固定，和输入规模没有直接的关系时，空间复杂度记做`O(1)`
        * 示例
            ```java
            void method(int n){
                int var = 3;
                ...
            }
            ```
    * 线性空间
        * 算法被分配一个线性集合，如数组，集合的大小和输入规模n成正比时，空间复杂度记作`O(n)`
        * 示例
            ```java
            void method(int n){
                int[] array = new int[n];
                ...
            }
            ```
    * 二维空间
        * 算法被分配一个二维数组集合，并且集合的长度和宽度斗鱼输入规模n成正比，空间复杂度记作`O(n^2)`
        * 示例
            ```java
            void method(int n){
                int[][] matrix = new int[n][n]
                ...
            }
            ```
    * 递归空间
        * 在递归代码中并没有显示的声明变量或集合，但是计算机在执行程序时，会专门分配一块内存，用来存储**方法调用栈**
        * 方法调用栈的两个行为
            * 进栈
                * 当进入一个新方法时，执行入栈操作，把**调用的方法和参数信息**压入栈中
            * 出栈
                * 方法返回时，执行出栈操作，把**调用的方法和参数信息**从栈中弹出
        * 示例
            ```java
            void method(int n){
                if (n<=1){
                    return;
                }

                method(n-1);
                ...
            }
            ```
        * 执行递归操作所需的内存空间和**递归的深度**成正比
            * 纯粹的递归操作的空间复杂度也是**线性的**，如果递归的深度是n，那么空间复杂度就是`O(n)`

## 时间与空间的取舍
[top](#catalog)
* 双重循环的时间复杂度`O(n^2)`，空间复杂度`O(1)`，属于时间换空间
* 通过字典来简化双重循环的时间复杂度`O(n)`，空间复杂度`O(n)`，属于空间换时间
* 多数情况下：时间复杂度更重要

# 数据结构基础
## 数组
[top](#catalog)
* 数组本身是在内存中是**顺序分配的**，可以进行随机访问，所以读取元素的时间复杂度为:`O(1)`
* 实现部分
    * `读取`的时间复杂度:`O(1)`
    * `更新`的时间复杂度:`O(1)`
    * `插入`的时间复杂度:`O(n)`，其中
        * 扩容的时间复杂度:`O(n)`
        * 移动元素的时间复杂度:`O(n)`
        * 两种时间复杂度相加并忽略系数后，时间复杂度为：`O(n)`
    * `删除`的时间复杂度:`O(n)`
        * 主要是移动元素的时间复杂度:`O(n)`
    ```java
    public class MyArray {
        private int[] array;
        private int size;

        public MyArray(int capacity){
            array = new int[capacity];
            size = 0;
        }

        //读取
        public int get(int index){
            if (index< 0 || index >= size){
                throw new IndexOutOfBoundsException("index不是有有效范围");
            }

            return array[index];
        }

        //更新
        public int set(int index, int value){
            if (index< 0 || index >= size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            int oldValue = array[index];
            array[index] = value;
            return oldValue;
        }

        //插入
        public void insert(int index, int value){
            //检验范围，忽略等于size，等于时直接在尾部添加
            if (index< 0 || index > size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            //检验size和capacity大小，容量不足时进行扩容
            if (size == array.length){
                reset();
            }

            //从右向左移动元素，从size-1到index
            for(int i = size-1; i>=index; i--){
                array[i+1] = array[i];
            }

            //插入元素
            array[index] = value;

            size++;
        }

        //删除
        public int delete(int index){
            if (index<0 || index >= size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            int oldValue = array[index];

            //移动元素,从index一直操作到倒数第二个
    //        for (int i = index; i < size-1; i++) {
    //            array[i] = array[i+1];
    //        }
    //        size--;

            //调整size
            size--;
            //移动size-index个元素，从index+1开始移动，移动到index（模拟System.arraycopy）
            int oldIndex = index + 1;
            for(int i=0; i < size - index; i++){
                array[index + i] = array[oldIndex + i];
            }

            array[size] = 0;
            return oldValue;
        }

        // 输出数组
        public void output(){
            int max = size - 1;
            StringBuilder buf = new StringBuilder();
            buf.append('[');
            for (int i = 0; i < size; i++) {
                if (i == max){
                    buf.append(array[i]);
                }else {
                    buf.append(array[i]);
                    buf.append(", ");
                }
            }
            buf.append(']');
            System.out.println(buf.toString());
        }

        //扩容底层数组,每次扩容为原来的两倍
        private void reset(){
            final int oldLength = array.length;
            //创建2倍的数组
            int[] newArray = new int[oldLength<<1];

            //从旧数组中拷贝数据
            for (int i = 0; i < oldLength; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
        }
    }
    ```
* 测试部分
    ```java
    public class MyArrayTest {
        @Test
        public void insertTest() {
            MyArray a = new MyArray(4);
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);
            a.insert(4, 55);

            a.output();
        }

        @Test
        public void  deleteTest(){
            MyArray a = new MyArray(4);
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);
            a.insert(4, 55);

            a.output();

            a.delete(2);
            a.output();
        }

        @Test
        public void  getTest(){
            MyArray a = new MyArray(4);
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);

            a.output();

            System.out.println(a.get(3));
        }

        @Test
        public void  setTest(){
            MyArray a = new MyArray(4);
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);

            a.output();

            System.out.println(a.set(2, 100));

            a.output();
        }
    }
    ```

## 链表
[top](#catalog)
* `获取节点`:链表只能顺序访问，即需要遍历链表，所以获取元素的(最坏)时间复杂度为:`O(n)`
* `更新节点`:和`获取节点`相同
* `插入/删除节点`:不考虑寻找阶段的过层，`插入/删除`自身的时间复杂度为`O(1)`
* 实现
    ```java
    class Node {
        int data;
        Node next;
        Node (int data){
            this.data = data;
        }

        public String toString(){
            return "[" + data + "]";
        }
    }

    // 单链表
    public class MyLinkedList {
        // 头节点
        Node head;
        // 尾节点，为了减少尾部添加节点的搜索过程，添加尾节点
        Node last;
        // 链表长度
        int size;

        // 读取
        public Node get(int index){
            if (index <0 || index >= size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            Node temp = head;
            //每次获取当前节点的下一个，直到index-1时，获取到index个节点
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }

            return temp;
        }

        // 更新
        public int update(int index, int value){
            Node target = get(index);
            int oldValue = target.data;
            target.data = value;

            return oldValue;
        }

        // 添加
        public void insert(int index, int value){
            // 忽略index=size，等于size时，在尾部添加节点
            if (index <0 || index > size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            // 创建节点
            Node newNode = new Node(value);

            //如果链别为空，则将首尾都设置为当前节点
            if (size==0){
                head = newNode;
                last = newNode;
            } else if (index == 0){
                //如果index=0，在头节点插入，则需要修改HEAD
                newNode.next = head;
                head = newNode;
            } else if (index == size){
                //如果index=size，则在尾部进行插入，需要修改尾节点
                last.next = newNode;
                last = newNode;
            } else {
                //在中间插入，需要找到:index-1节点，来进行连接
                Node prev = get(index-1);
                newNode.next = prev.next;
                prev.next = newNode;
            }

            size++;
        }

        // 删除
        public Node delete(int index){
            if (index <0 || index >= size){
                throw new IndexOutOfBoundsException("index不是有效范围");
            }

            Node delNode;
            if (index == 0){
                //如果删除头节点，则需要修改head
                delNode = head;
                head=head.next;
            } else if (index == size - 1){
                //如果删除尾节点，则需要修改last
                delNode = last;
                Node newLast = get(index - 1);
                last = newLast;
                last = null;
            } else {
                //如果删除中间节点，则需要获取index-1节点，并连接index.next
                Node prev = get(index - 1);
                delNode = prev.next;
                prev.next = prev.next.next;
            }

            size--;
            // 链表为空时，清空头尾节点
            if (size==0){
                head = null;
                last = null;
            }
            return delNode;
        }

        // 打印链表
        public void output(){
            Node temp = head;
            while (temp!=null){
                System.out.println(temp.data);
                temp = temp.next;
            }
        }
    }
    ```
* 测试
    ```java
    public class MyLinkedListTest {
        @Test
        public void insertTest() {
            MyLinkedList a = new MyLinkedList();
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);
            a.insert(2, 55);

            a.output();
        }

        @Test
        public void  deleteTest(){
            MyLinkedList a = new MyLinkedList();
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);
            a.insert(4, 55);

            a.output();

            a.delete(2);
            a.output();
        }

        @Test
        public void  getTest(){
            MyLinkedList a = new MyLinkedList();
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);

            a.output();

            System.out.println(a.get(2));
            System.out.println(a.get(3));
            System.out.println(a.get(0));
        }

        @Test
        public void  updateTest(){
            MyLinkedList a = new MyLinkedList();
            a.insert(0, 11);
            a.insert(1, 22);
            a.insert(2, 33);
            a.insert(3, 44);

            a.output();

            System.out.println(a.update(2, 100));

            a.output();
        }
    }
    ```
## 数组与链表
[top](#catalog)
* 操作的时间复杂度比较

||读取|更新|插入|删除|
|-|-|-|-|-|
|数组|`O(1)`|`O(1)`|`O(n)`|`O(n)`|
|链表|`O(n)`|`O(1)`|`O(1)`|`O(1)`|

* 数组的优势:快速随机访问
    * 适合于读操作多，写操作少的场景
* 链表的优势:快速的插入、删除
    * 适合于频繁增删的场景

## 栈和队列
[top](#catalog)
* 常用物理结构于逻辑结构

||线性结构|非线性结构|
|-|-|-|
|**逻辑结构**|顺序表、栈、队列|树、图|
|**物理结构**|数组|链表|

### 栈stack
[top](#catalog)
* 栈是一种线性结构
* 使用规则
    * 先入后出，First In Last Out，FILO
    * 只能从一端进行操作
* 最早进入的位置为**栈底bottom**，最后进入的为**栈顶top**
* 栈的基本操作
    * 入栈push
    * 出栈pop
* **时间复杂度**
    * 由于只能操作栈顶元素，所以无论物理结构是什么，时间复杂度都是`O(1)`
* 栈的应用
    * 逆序输出
    * 对`历史`的回溯
    * 用栈来替代递归
* 实现
    ```java
    public class MyArrayStack {
        private MyArray array;

        public MyArrayStack(int capacity){
            array = new MyArray(capacity);
        }

        //入栈
        public void push(int data){
            array.insert(array.length(), data);
        }

        //出栈
        public int pop(){
            return array.delete(array.length()-1);
        }

        //打印栈中的内容
        public void output(){
            array.output();
        }
    }
    ```
* 测试
    ```java
    public class MyArrayStackTest {
        @Test
        public void pushTest(){
            MyArrayStack a = new MyArrayStack(5);
            a.push(11);
            a.push(12);
            a.push(13);
            a.push(14);
            a.push(15);
            a.output();
        }

        @Test
        public void popTest(){
            MyArrayStack a = new MyArrayStack(5);
            a.push(11);
            a.push(12);
            a.push(13);
            a.push(14);
            a.push(15);
            a.output();
            System.out.println(a.pop());
            a.output();
            System.out.println(a.pop());
            a.output();
            System.out.println(a.pop());
        }
    }
    ```

### 队列queue
[top](#catalog)
* 队列是一种线性数据结构
* 队列中的元素只能先进先出，First In First Out，FIFO
* 出队端:队头`front`，队尾`rear`
    * 需要从队尾进行入队
* 队列的基本操作
    * 入队enqueue
        * 只能在队尾进行操作
        * 时间复杂度:`O(1)`
    * 出队dequeue
        * 只能在队头操作
        * 时间复杂度:`O(1)`
* 队列的应用
    * `历史`的复现
    * 争夺公平锁的等待队列

* 基本实现
    ```java
    public class MyQueue {
        private int[] array;
        private int capacity;
        private int front;
        private int rear;

        public MyQueue(int capacity){
            this.capacity = capacity;
            array = new int[capacity];
        }

        //入队
        public void enqueue(int data) throws Exception {
            //检查队列是否已满
            if ((rear + 1)%array.length == front){
                throw new Exception("队列已满");
            }

            array[rear] = data;
            rear = (rear + 1)%array.length;
        }

        //出队
        public int dequeue() throws Exception {
            //检查队列是否为空
            if (rear == front){
                throw new Exception("队列为空");
            }

            int value = array[front];
            front = (front + 1) % array.length;
            return value;
        }

        public int size(){
            return (rear + capacity - front)%capacity;
        }

        //打印
        public void output(){
            StringBuilder buf = new StringBuilder();
            buf.append('[');
            int end = front + size();
            int max = end - 1;
            for (int i=front; i < end; i++){
                buf.append(array[i % capacity]);
                if (i != max) {
                    buf.append(", ");
                }
            }
            buf.append(']');
            System.out.println(buf.toString());
        }
    }
    ```
* 测试
    ```java
    public class MyQueueTest {
        @Test
        public void enqueueTest() throws Exception {
            MyQueue a = new MyQueue(4);
            a.enqueue(1);
            a.enqueue(2);
            a.enqueue(3);

            a.output();
        }

        @Test
        public void dequeueTest() throws Exception {
            MyQueue a = new MyQueue(4);
            a.enqueue(1);
            a.enqueue(2);
            a.enqueue(3);

            a.output();

            System.out.println(a.dequeue());
            System.out.println(a.dequeue());
            a.output();

            a.enqueue(11);
            a.enqueue(12);
            a.output();
        }
    }
    ```
### 双端队列
[top](#catalog)
* 融合了队列和栈，队头和队尾都可以进行入队出队

## 散列表hashTable
[top](#catalog)
* 即字典，提供了key于value的对应关系
* 一个key对应一个value，读取数据的时间复杂度:`O(1)`
* hash函数
    * 底层通过数组进行存储
    * 对key使用hash后返回一个`int`型的hashCode，再和数组长度取模获得存储位置进行存储
        * index的计算方法:`hash(key) % array.length`
* 散列表的写操作--解决hash冲突
    * 不同的key的hashCode可能会相同
    * 解决方法
        * 开放寻址法
            * 向后移动：当hashCode发送冲突时，将存储的index向后移动，直到找到一个未使用的index
        * 链表法
            * 数组的每个元素不仅是一个Entry，还是是一个链表的头节点
            * 当产生hash冲突时，则添加到链表的末尾
* 散列表的读操作
    * 通过hashCode确定数组的index
    * 通过index获取Entry
    * 于Entry中的key比较，如果相同则返回value，如果不相同，则顺着链表继续比较
* 散列表的扩容
    * 多次插入元素后，散列表达到一定的**饱和度**，Key映射发生冲突的概率会提高
        * 冲突率提高后，大量元素会存在相同下标的链表中，**链表会很长**，影响**读取和插入**的性能
        * 此时需要考虑进行**扩容**
    * HashMap的扩容的两个因素
        * `Capacity`，HashMap的当前长度
        * `LoadFactor`,HashMap的负载因子，**默认值为0.75f**
    * HashMap执行扩容的条件：`HashMap.Size ?= Capacity * LoadFactor`
    * 扩容的步骤
        * `扩容`:创建一个新数组，长度为原来的2倍
        * `重新hash`:由于`array.length`变了，所以Entry需要重新hash到新数组中
    * 扩容后，散列表变大稀疏，使Entry能够尽可能均匀的分配到数组中

# 树
[top](#catalog)
* 定义
    * 树是n(n>=0)个节点的有限集，当`n=0`时，成为空树。在任意一个非空树中，有如下特点
        1. 有且仅有一个特定的称为根的节点
        2. 当`n>1`时，其余节点可分为m(m>0)个互不相交的有限集，每一个集合本身又是一个树，并称为根的子树
* 树的最大层级数=树的高度/深度
* 基本概念
    * 节点下的是孩子节点
    * 没有孩子节点的是叶子节点

## 二叉树
[top](#catalog)
* 每个节点**最多有两个**孩子节点：左孩子、右孩子
* 二叉树的两种特殊形式
    * 满二叉树
        * 定义
            * 所有非叶子节点都有左孩子和右孩子节点
            * 所有叶子节点都在同一层
    * 完全二叉树
        * 定义
            * 对于一个二叉树按层级顺序编号，从1到n，且这个树所有的节点和同样深度的满二叉树的编号顺序相同
        * 需要保证只有最后一层有节点不全
* 使用链式存储结构
    * 链式结构是二叉树最直观的存储方式
    * 节点的组成
        * `data`，数值
        * `left`，左子树指针
        * `right`，右子树指针
* 使用数组存储
    * 从根节点，按照层级顺序从0编号，如果某个节点没有则`+1`
    * 计算方法
        * 设父节点为`parent`
            * 左孩子节点:`parent * 2 + 1`
            * 左孩子节点:`parent * 2 + 2`
        * 设某个左孩子节点为`leftChild`，父节点为`(leftChild - 1) / 2`
        * 设某个右孩子节点为`rightChild`，父节点为`(rightChild - 2) / 2`
    * 使用数组来保存**稀疏二叉树**，会非常浪费空间

## 二叉树的应用
[top](#catalog)
* 查找
    * 二叉查找树
* 维持相对顺序

* 二叉查找树/二叉排序树
    * 主要作用是进行查找操作
    * 几个条件
        * 如果左子树不为空，则左子树上所有节点的值均**小于**根节点的值
        * 如果右子树不为空，则右子树上所有节点的值均**大于**根节点的值
        * 左右子树也都是二叉查找树
    * 对于一个节点分布相对均衡的二叉查找树来说，如果节点总数是n，则搜索的时间复杂度是:`O(logn)`
        * 查找时，不停的切换左右子树，相当于`n/2`，即`logn`
        * 类似于二分查找
    * 二叉排序树的问题-退化
        * 所有的节点都是左孩子节点，导致树变成的链表,时间复杂度变为：`O(n)`
        * 解决方法：`二叉树的自平衡`
            * 红黑树、AVL树、树堆等
* 维持相对顺序
    * 仍然使用二叉排序树
    * 二叉排序树要求左子树全部小于根节点，右子树全部大于根节点，**保证了二叉树的有序性**

## 二叉树的遍历
[top](#catalog)
* 从节点之间的位置划分遍历方式
    * 前序遍历
        * 输出顺序：根节点、左子树、右子树(中左右)
    * 中序遍历
        * 输出顺序：左子树、根节点、右子树(左中右)
        * 如果某个节点还有左孩子，则继续深入遍历下去，一直找到不再有左孩子的节点，并输出该节点
    * 后序遍历
        * 输出顺序：左子树、右子树、根节点(左右中)
    * 层序遍历
* 两大类遍历
    * 深度优先遍历
        * 前序遍历
        * 中序遍历
        * 后序遍历
    * 广度优先遍历
        * 层序遍历

### 深度优先遍历
#### 使用链表进行递归
[top](#catalog)
* 实现
    ```java
    class TreeNode{
        int data;
        TreeNode left;
        TreeNode right;

        TreeNode(int data){
            this.data = data;
        }
    }
    public class MyTreeByLink {
        // 构造二叉树
        public static TreeNode makeTree(LinkedList<Integer> list){
            //如果list为空则返回null
            if (list == null || list.isEmpty()){
                return null;
            }

            TreeNode node = null;

            //从list中抽取第一个元素
            Integer data = list.removeFirst();

            //如果数据为null，则已经到达叶子节点，不再进行递归
            //直接给叶子上一层的叶子节点返回null
            if (data != null){
                node = new TreeNode(data);
                node.left = makeTree(list);
                node.right = makeTree(list);
            }

            return node;
        }

        //前序遍历：中左右
        public static void preOrderTraversal(TreeNode tree){
            if (tree == null){
                return;
            }

            System.out.println(tree.data);
            preOrderTraversal(tree.left);
            preOrderTraversal(tree.right);
        }

        //中序遍历：左中右
        public static void inOrderTravesal(TreeNode tree){
            if (tree == null){
                return;
            }
            inOrderTravesal(tree.left);
            System.out.println(tree.data);
            inOrderTravesal(tree.right);
        }

        //后序遍历：左右中
        public static void postOrderTraversal(TreeNode tree){
            if (tree == null){
                return;
            }
            postOrderTraversal(tree.left);
            postOrderTraversal(tree.right);
            System.out.println(tree.data);
        }
    }
    ```
* 测试
    ```java
    public class MyTreeByLinkTest {
        @Test
        public void test01(){
            LinkedList<Integer> list = new LinkedList<>(
                    Arrays.asList(new Integer[] {3, 2, 9, null, null, 10, null, null, 8, null, 4})
            );

            TreeNode tree = MyTreeByLink.makeTree(list);
            System.out.println("preOrderTraversal");
            MyTreeByLink.preOrderTraversal(tree); //3 2 9 10 8 4
            System.out.println("inOrderTravesal");
            MyTreeByLink.inOrderTravesal(tree); //9 2 10 3 8 4
            System.out.println("postOrderTraversal");
            MyTreeByLink.postOrderTraversal(tree); //9 10 2 4 8 3
        }
    }
    ```
#### 使用栈来实现
* 实现
    ```java
    public class MyTreeByStack {
        public static void preOrderTraversal(TreeNode root){
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            while (treeNode != null || !stack.isEmpty()){
                // 3.遍历右子树
                // 1.不断输出当前节点，然后入栈，然后遍历左子树，直到左子树为空
                while (treeNode != null){
                    System.out.println(treeNode.data);
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                }

                // 4.右子树为空时，将父节点出栈，然后重复1
                // 2.左子树为空后，出栈，然后遍历右子树
                if (!stack.isEmpty()){
                    treeNode = stack.pop();
                    treeNode = treeNode.right;
                }
            }
        }

        public static void inOrderTraversal(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            while (treeNode != null || !stack.isEmpty()) {
                while (treeNode != null) {
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                }

                if (!stack.isEmpty()) {
                    treeNode = stack.pop();
                    System.out.println(treeNode.data);
                    treeNode = treeNode.right;
                }
            }
        }

        public static void postOrderTraversal(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            TreeNode pre = null;

            while (treeNode != null || !stack.isEmpty()) {
                while (treeNode != null) {
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                }

                // 此时，左节点全部遍历完，看栈顶
                treeNode = stack.peek();

                //检查有节点
                //左右节点全部为null，则说明是叶子节点，出栈并输出
                //右节点已经被使用过，则说明左右孩子节点都遍历完，出栈并输出
                //输出节点后，该节点已使用完，将treeNode置为null，进行下一次遍历
                if (treeNode.right == null || treeNode.right == pre)
                {
                    treeNode = stack.pop();
                    System.out.println(treeNode.data);
                    pre = treeNode;
                    treeNode = null;
                } else {
                    //右节点没遍历过，将当前节点置为右节点，执行下一次遍历
                    treeNode = treeNode.right;
                }
            }
        }

        public static void preOrderTraversal02(TreeNode root){
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            while (treeNode != null || !stack.isEmpty()){
                if (treeNode != null){
                    System.out.println(treeNode.data);
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                } else {
                    treeNode = stack.pop();
                    treeNode = treeNode.right;
                }
            }
        }

        public static void inOrderTraversal02(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            while (treeNode != null || !stack.isEmpty()) {
                if (treeNode != null) {
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                } else {
                    treeNode = stack.pop();
                    System.out.println(treeNode.data);
                    treeNode = treeNode.right;
                }
            }
        }
    }
    ```
* 测试
    ```java
    public class MyTreeByStackTest {
        @Test
        public void test01(){
            LinkedList<Integer> list = new LinkedList<>(
                    Arrays.asList(new Integer[] {3, 2, 9, null, null, 10, null, null, 8, null, 4})
            );

            TreeNode tree = TreeNode.makeTree(list);
            System.out.println("preOrderTraversal");
            MyTreeByStack.preOrderTraversal(tree); //3 2 9 10 8 4
            System.out.println("preOrderTraversal02");
            MyTreeByStack.preOrderTraversal02(tree); //3 2 9 10 8 4
            System.out.println("inOrderTravesal");
            MyTreeByStack.inOrderTraversal(tree); //9 2 10 3 8 4
            System.out.println("inOrderTraversal02");
            MyTreeByStack.inOrderTraversal02(tree); //9 2 10 3 8 4
            System.out.println("postOrderTraversal");
            MyTreeByStack.postOrderTraversal(tree); //9 10 2 4 8 3
        }
    }
    ```

### 广度优先遍历
#### 层序遍历
[top](#catalog)
* 同一层之间的节点是没有联系的，需要**队列**来辅助
* 实现
    ```java
    public class MyTreeByQueue {
        public static void levelOrderTraversal(TreeNode root){
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            q.offer(root);
            while (!q.isEmpty()){
                TreeNode node = q.poll();
                System.out.println(node.data);
                if (node.left != null){
                    q.offer(node.left);
                }

                if (node.right != null){
                    q.offer(node.right);
                }
            }
        }
    }
    ```
* 测试
    ```java
    public class MyTreeByQueueTest {
        @Test
        public void method(){
            LinkedList<Integer> list = new LinkedList<>(
                    Arrays.asList(new Integer[] {3, 2, 9, null, null, 10, null, null, 8, null, 4})
            );

            TreeNode tree = TreeNode.makeTree(list);
            MyTreeByQueue.levelOrderTraversal(tree); //3 2 8 9 10 4
        }
    }
    ```

## 二叉堆
[top](#catalog)
* 二叉堆本质上是一种**完全二叉树**
* 二叉堆的根节点称为**堆顶**
* 两种类型
    * 最大堆
        * `任何一个父节点的值 >= 左孩子、有孩子节点的值`
        * 堆顶是最大值
    * 最小堆
        * `任何一个父节点的值 <= 左孩子、有孩子节点的值`
        * 堆顶是最小值
* 二叉堆的自我调整
    * `自我调整`：通过操作，将一个不符合条件的完全二叉树，调整成一个二叉堆
    * 几种操作
        * 插入节点
            * 即让新节点**上浮**
            * 插入的位置是完全二叉树的最后一个位置
            * 插入节点后，不停的和父节点进行比较，如果不符合条件就就进行交换
                * 最大二叉堆：目标节点比父节点大，则目标节点上浮到父节点的位置
                * 最小二叉堆：目标节点比父节点小，则目标节点上浮到父节点的位置
            * 时间复杂度：`O(logn)`
        * 删除节点
            * 即让节点**下沉**
            * 一般指删除**堆顶**元素
            * 删除后，将最后一个元素设为堆顶，然后不停的与左右孩子节点进行表，与最大/最小值(最大堆/最小堆)比较，不符合条件时，与孩子节点进行交换
            * 时间复杂度：`O(logn)`
        * 构建二叉堆
            * 让所有非叶子节点依次下沉
            * 把一个无序的二叉树调整为二叉堆
* 实现
    ```java
    public class MyBinaryHeapTree {

        //最小堆 插入 上浮
        public static void minHeapUpAdjust(int[] tree) {
            //获取末尾元素index 和插入元素的值
            int childIndex = tree.length - 1;
            int temp = tree[childIndex];

            //计算父节点index
            int parentIndex = (childIndex - 1) / 2;//使用整除，即使是右孩子节点也能找到父节点

            //遍历条件：最终的index>0 并且 插入元素的<父节点的值
            while (childIndex > 0 && temp < tree[parentIndex]){
                tree[childIndex] = tree[parentIndex];
                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2; //使用整除，即使是右孩子节点也能找到父节点
            }

            //将插入元素设定到目标位置
            tree[childIndex] = temp;
        }

        // 最小堆 删除 下沉
        public static void minHeapDownAdjust(int[] tree, int parentIndex, int length){
            int temp = tree[parentIndex];
            int childIndex = 2 * parentIndex + 1; //定位到左孩子节点

            while (childIndex < length){
                // 找出左右孩子节点中较小的那个节点
                if (tree[childIndex + 1] < tree[childIndex]){
                    childIndex++;
                }
                
                // 如果小于所有子节点，则结束
                if (temp < tree[childIndex]){
                    break;
                }

                tree[parentIndex] = tree[childIndex];
                parentIndex = childIndex;
                childIndex = 2 * parentIndex + 1;// 重新定位到左孩子节点
            }

            tree[parentIndex] = temp;
        }

        public static void buildMinHeap(int[] tree){
            //从最后一个非叶子节点开始进行【下沉】处理
            for (int i = (tree.length - 2) / 2; i>=0; i--) {
                minHeapDownAdjust(tree, i, tree.length);
            }
        }
    }
    ```
* 测试
    ```java
    public class MyBinaryHeapTreeTest {
        @Test
        public void minHeapUpAdjustTest() {
            int[] tree = new int[]{1,3,2,6,5,7,8,9,10,0};
            MyBinaryHeapTree.minHeapUpAdjust(tree);
            System.out.println(Arrays.toString(tree));
        }

        @Test
        public void minHeapDownAdjustTest() {
            int[] tree = new int[]{7,1,3,2,6,5,8,9,10};
            MyBinaryHeapTree.minHeapDownAdjust(tree, 0, tree.length);
            System.out.println(Arrays.toString(tree)); //[1, 2, 3, 7, 6, 5, 8, 9, 10]
        }

        @Test
        public void buildMinHeapTest() {
            int[] tree = new int[]{7,1,3,2,6,5,8,9,10};
            MyBinaryHeapTree.buildMinHeap(tree);
            System.out.println(Arrays.toString(tree));
        }
    }
    ```

## 优先队列
[top](#catalog)
* 普通队列的规则：队尾入队，队头出队
* 优先队列的规则：
    * 最大优先队列：忽略入队顺序，**最大值**优先出队
    * 最小优先队列：忽略入队顺序，**最小值**优先出队
* 利用二叉堆的性质来实现优先队列
    * 最大优先队列--最大二叉堆
    * 最小优先队列--最小二叉堆
    * 入队操作=插入元素，出队操作=删除栈顶元素
    * `出队/入队`的时间复杂度：`O(logn)`
* 实现
    ```java
    //最大优先队列
    public class MyMaxPriorityQueue {
        private int[] array;
        private int size;

        public MyMaxPriorityQueue(int capacity){
            array = new int[capacity];
        }

        //扩容
        private void resize(){
            array = Arrays.copyOf(array, array.length * 2);
        }

        //入队
        public void enqueue(int data){
            //如果空间不足则进行扩容
            if (size >= array.length){
                resize();
            }

            //插入数组尾部
            array[size] = data;
            size++;

            //进行调整：上浮
            upAdjust();
        }

        //上浮
        private void upAdjust(){
            int childIndex = size - 1;
            int parentIndex = (childIndex - 1) / 2;
            int temp = array[childIndex];

            while (childIndex > 0 && temp > array[parentIndex]){
                // 父节点下移到子节点
                array[childIndex] = array[parentIndex];
                // 重新极端index
                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2;
            }

            array[childIndex] = temp;
        }

        //出队
        public int dequeue() throws Exception {
            //检查是否有元素
            if (size == 0){
                throw new Exception("队列为空");
            }

            //删除栈顶元素
            int head = array[0];
            size--;
            array[0] = array[size];//末尾元素移动到栈顶
            array[size] = 0;//将末尾元素设为0值

            //调整：下沉
            downAdjust();

            return head;
        }

        //下沉
        private void downAdjust(){
            int parentIndex = 0; //从栈顶开始下沉
            int childIndex = 1; //定位到左孩子节点
            int temp = array[parentIndex];

            while (childIndex < size){
                //左孩子、右孩子节点中最大的节点
                //需要保证当前节点有右孩子节点
                if (childIndex+1 < size && array[childIndex + 1] > array[childIndex]){
                    childIndex++;
                }

                //如果大于【等于】孩子节点，则已经成为最大二叉堆，不需要再继续调整
                if (temp >= array[childIndex]){
                    break;
                }

                //将孩子节点移动到父节点
                array[parentIndex] = array[childIndex];
                parentIndex = childIndex;
                childIndex = parentIndex * 2 + 1;//定位到左孩子节点
            }

            array[parentIndex] = temp;
        }

        // 打印当前队列中的元素值
        public void print(){
            System.out.println(Arrays.toString(Arrays.copyOf(array, size)));
        }
    }
    ```
* 测试
    ```java
    public class MyArrayStackTest {
        @Test
        public void pushTest(){
            MyArrayStack a = new MyArrayStack(5);
            a.push(11);
            a.push(12);
            a.push(13);
            a.push(14);
            a.push(15);
            a.output();
        }

        @Test
        public void popTest(){
            MyArrayStack a = new MyArrayStack(5);
            a.push(11);
            a.push(12);
            a.push(13);
            a.push(14);
            a.push(15);
            a.output();
            System.out.println(a.pop());
            a.output();
            System.out.println(a.pop());
            a.output();
            System.out.println(a.pop());
        }
    }
    ```

# 排序算法
[top](#catalog)
* 根据时间复杂度进行分类
    * `O(n^2)`
        * 冒泡排序
        * 选择排序
        * 插入排序
        * 希尔排序
            * 希尔排序比较特殊，性能优于`O(n^2)`,但又大于`O(nlogn)`
    * `O(nlogn)`
        * 快速排序
        * 归并排序
        * 堆排序
    * 时间复杂度为**线性的排序算法**
        * 计数排序
        * 桶排序
        * 基数排序
* 根据其稳定性划分：
    * 稳定排序
        * 值相同的元素，在排序后**仍然保持**着相同的顺序
    * 不稳定排序
        * 值相同的元素，在排序后**无法保持**着相同的顺序
* 交换排序
    * 冒泡排序
    * 快速排序

## 冒泡排序
[top](#catalog)
* 冒泡排序本身是单向的：从左到有，将最大/最小的元素移动到右边
* 两种优化方法
    * 记录本轮循环是否有序，如果有序，则说明整体以有序
    * 记录有序边界，减少循环的论述，和单次循环的比较次数
* 实现
    ```java
    public class MyBubbleSort {
        public static void maxSort01(int[] list){
            int temp;
            for (int i = 0; i < list.length - 1; i++) {
                for (int j = 0; j < list.length - 1 - i; j++) {
                    if (list[j] > list[j+1]){
                        temp = list[j+1];
                        list[j+1] = list[j];
                        list[j] = temp;
                    }
                }
            }
        }

        // 优化方法1，有序停止
        public static void maxSort02(int[] list){
            int temp;
            boolean isSorted;
            for (int i = 0; i < list.length - 1; i++) {
                isSorted = true;
                for (int j = 0; j < list.length - 1 - i; j++) {
                    if (list[j] > list[j + 1]){
                        isSorted = false; //记录本次循环中是否已达到有序，没有交换则有序
                        temp = list[j + 1];
                        list[j + 1] = list[j];
                        list[j] = temp;
                    }
                }

                if (isSorted)
                    break;
            }
        }

        // 优化方法2，增加有序边界
        public static void maxSort03(int[] list){
            int temp;
            boolean isSorted;
            int sortBorder = list.length - 1;
            int lastChangeIndex = 0;
            for (int i = 0; i < list.length - 1; i++) {
                isSorted = true;

                for (int j = 0; j < sortBorder; j++) {
                    if (list[j] > list[j+1]){
                        temp = list[j + 1];
                        list[j + 1] = list[j];
                        list[j] = temp;
                        lastChangeIndex = j; //每次保存最后进行调整的位置作为有序边界
                        isSorted = false;
                    }
                }

                if (isSorted) {
                    break;
                }

                sortBorder = lastChangeIndex; //重新设定有序边界
            }

        }
    }
    ```
* 测试
    ```java
    public class MyBubbleSortTest {
        @Test
        public void maxSort01Test(){
            int[] a = new int[] {5,3,7,2,6,4,8,10,1};
            System.out.println(Arrays.toString(a));
            MyBubbleSort.maxSort01(a);
            System.out.println(Arrays.toString(a));
        }

        @Test
        public void maxSort02Test(){
            int[] a = new int[] {5,3,7,2,6,4,8,10,1};
            System.out.println(Arrays.toString(a));
            MyBubbleSort.maxSort02(a);
            System.out.println(Arrays.toString(a));
        }
        @Test
        public void maxSort03Test(){
            int[] a = new int[] {3, 4, 2, 1, 5, 6, 7, 8};
            System.out.println(Arrays.toString(a));
            MyBubbleSort.maxSort03(a);
            System.out.println(Arrays.toString(a));
        }
    }
    ```
## 鸡尾酒排序
[top](#catalog)
* 冒泡排序的一种优化
* 从左向右，然后在从右向左，当某一轮达到有序时则停止
* 鸡尾酒排序能有效的减少循环的轮数，但是会使代码量增加一倍
* 适用于**大部分元素已经有序**的情况
* 实现
    ```java
    public class MyCocktailSort {
        // 从左到右，再从右到左 为一轮
        public static void maxSort01(int[] list){
            int temp;
            boolean isSorted;

            // 从左到右，再从右到左 为一轮
            for (int i = 0; i < list.length/2; i++) {
                //从左到右
                isSorted = true;
                for (int j = i; j < list.length - 1 - i; j++) {
                    if (list[j] > list[j+1]){
                        isSorted = false;
                        temp = list[j+1];
                        list[j + 1] = list[j];
                        list[j] = temp;
                    }
                }

                if (isSorted){
                    break;
                }

                //从右到左
                isSorted = true;
                for (int j = list.length - 1 - i; j > i; j--) {
                    if (list[j] < list[j - 1]) {
                        isSorted = false;
                        temp = list[j];
                        list[j] = list[j - 1];
                        list[j - 1] = temp;
                    }
                }

                if (isSorted) {
                    break;
                }
            }
        }

        // 使用边界来减少轮数和比较次数
        public static void maxSort02(int[] list){
            int temp;
            boolean isSorted;
            int firstSortBorder = list.length - 1; //从左到右的边界
            int firstChangeIndex = 0; //保存从左到右的边界变化
            int secondSortBorder = 0; //从右到左的边界
            int secondChangeIndex = list.length - 1; //保存从右到左的边界变化

            // 从左到右，再从右到左 为一轮
            for (int i = 0; i < list.length/2; i++) {
                //从左到右
                isSorted = true;
                for (int j = secondSortBorder; j < firstSortBorder; j++) {
                    if (list[j] > list[j+1]){
                        isSorted = false;
                        firstChangeIndex = j;
                        temp = list[j+1];
                        list[j + 1] = list[j];
                        list[j] = temp;
                    }
                }

                if (isSorted){
                    break;
                }
                firstSortBorder = firstChangeIndex;


                //从右到左
                isSorted = true;
                for (int j = firstSortBorder; j > secondSortBorder; j--) {
                    if (list[j] < list[j - 1]) {
                        isSorted = false;
                        secondChangeIndex = j;
                        temp = list[j];
                        list[j] = list[j - 1];
                        list[j - 1] = temp;
                    }
                }

                if (isSorted) {
                    break;
                }

                secondSortBorder = secondChangeIndex;
            }
        }
    }
    ```
* 测试
```java
public class MyCocktailSortTest {
    @Test
    public void maxSort01Test(){
        int[] a = new int[] {2, 3, 4, 5, 6, 7, 8, 1};
        System.out.println(Arrays.toString(a));
        MyCocktailSort.maxSort01(a);
        System.out.println(Arrays.toString(a));
    }

    @Test
    public void maxSort02Test(){
        int[] a = new int[] {2, 3, 4, 5, 6, 7, 8, 1};
        System.out.println(Arrays.toString(a));
        MyCocktailSort.maxSort02(a);
        System.out.println(Arrays.toString(a));
    }
}
```

## 快速排序
[top](#catalog)
* 快速排序是从**冒泡排序**演变过来的
* 平均复杂度为：`O(nlogn)`，最坏情况的时间复杂度：`O(n^2)`
* **分治法**：
    * 每一轮挑选一个基准元素，将比基准元素大的移动到一边，比基准元素小的移动到另一边，**把数组拆成两部分**
    * 数组每一轮都被拆成两部分，每一部分在下一轮又被拆成两部分，直到不可分为止
* 每一轮都要遍历n个元素，平均要做`logn`轮，所以**平均时间复杂度**为：`O(nlogn)`
* 基准元素的选择
    * 最简单的方式：**选择每个数列的第一个元素**
        * 每个部分都选择第一个元素的问题：
            * 如果整个数组是一个**逆序数组**
                * 第一个元素会变成**最大值或最小值**，无法将数组分成两部分
                * 时间复杂度会退化成：`O(n^2)`
    * 随机选择一个元素，然后与数列的首个元素进行交换
        * 如果选择的元素是整个数列的最大值、最小值，仍然会影响分治的效果
* 元素的交换-双边法