package com.ljs.learn.datastructure.linkedlist.single.base;

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
            System.out.println("null");
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


