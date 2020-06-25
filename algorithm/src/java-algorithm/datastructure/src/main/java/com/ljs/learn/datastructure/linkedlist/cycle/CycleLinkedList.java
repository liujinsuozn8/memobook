package com.ljs.learn.datastructure.linkedlist.cycle;

public class CycleLinkedList<T extends Comparable> {
    private LinkedNode<T> first;
    private LinkedNode<T> end;

    public LinkedNode<T> getFirst() {
        return first;
    }

    public LinkedNode<T> getEnd() {
        return end;
    }

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