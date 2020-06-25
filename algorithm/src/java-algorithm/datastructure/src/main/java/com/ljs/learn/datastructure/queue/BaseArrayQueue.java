package com.ljs.learn.datastructure.queue;

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
