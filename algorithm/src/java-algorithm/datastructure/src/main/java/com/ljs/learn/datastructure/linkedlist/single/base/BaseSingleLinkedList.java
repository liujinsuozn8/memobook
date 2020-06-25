package com.ljs.learn.datastructure.linkedlist.single.base;

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