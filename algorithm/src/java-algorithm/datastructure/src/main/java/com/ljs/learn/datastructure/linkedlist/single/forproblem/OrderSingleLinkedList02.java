package com.ljs.learn.datastructure.linkedlist.single.forproblem;


import java.util.Stack;

public class OrderSingleLinkedList02<T extends Comparable> {
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

        while (temp.next != null) { // 如果链表为空，将直接退出
            if (temp.next.compareTo(data) == 0) {
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

    // 获取链表中有效结点的个数
    public int length(){
        OrderLinkedNode<T> temp = head.next;
        int length = 0;
        while(temp != null){
            length++;
            temp = temp.next;
        }

        return length;
    }

    // 获取链表中倒数第k个结点
    public T findLastIndexNode(int index){
        if (head.next == null){ return null; }
        int length = this.length();
        if (index <=0||index > length){ return null; }
        OrderLinkedNode<T> temp = head.next;
        for(int i=0; i< length - index; i++){
            temp = temp.next;
        }

        return temp.data;
    }

    // 反转链表
    public void reverse(){
        if (head.next == null || head.next.next == null){ return; }

        OrderLinkedNode<T> currentNode = head.next;
        OrderLinkedNode<T> nextNode = null;
        OrderLinkedNode<T> reversedList = null;

        while (currentNode != null){
            nextNode = currentNode.next;
            currentNode.next = reversedList;
            reversedList = currentNode;
            currentNode = nextNode;
        }

        head.next = reversedList;
    }

    // 逆序打印链表
    public void reverseShowList(){
        if (head.next == null){
            System.out.println("null list");
            return;
        }

        Stack<OrderLinkedNode<T>> stack = new Stack<>();

        OrderLinkedNode<T> temp = head.next;
        while (temp != null){
            stack.push(temp);
            temp = temp.next;
        }

        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }

    // 合并另外一个有序的单链表
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
}

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


