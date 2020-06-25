package com.ljs.learn.datastructure.stack.arraystack;

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
