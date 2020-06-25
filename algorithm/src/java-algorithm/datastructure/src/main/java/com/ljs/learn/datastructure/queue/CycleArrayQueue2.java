package com.ljs.learn.datastructure.queue;

public class CycleArrayQueue2<T> {
    private int maxSize;
    private int front;
    private int rear;
    private T[] container;

    public CycleArrayQueue2(int maxSize) {
        this.maxSize = maxSize;
        front = 0;
        rear = -1;
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

        // 需要取余，防止溢出
        rear = (rear + 1) % maxSize;

        container[rear] = elem;
    }

    public T take(){
        if (isEmpty()){return null;}
        front = (front + 1) % maxSize;

        T elem = container[front];
        return elem;
    }

    public T peek(){
        if (isEmpty()){return null;}

        return container[front + 1];
    }

    public void showQueue(){
        for (int i = front; i< front + size(); i++){
            System.out.println(container[i % maxSize]);
        }
    }

    // 返回队列中的有效数据个数
    public int size(){
        return (rear + maxSize - front + 1) % maxSize;
    }
}
