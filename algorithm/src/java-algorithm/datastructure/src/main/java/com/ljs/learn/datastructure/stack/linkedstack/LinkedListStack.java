package com.ljs.learn.datastructure.stack.linkedstack;

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

class LinkedNode<T>{
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
}
