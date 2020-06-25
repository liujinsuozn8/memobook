package com.ljs.learn.datastructure.linkedlist.single.forproblem;


import java.util.Stack;

public class BaseSingleLinkedList02<T extends Comparable> {
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

    // 反转链表
    public void reverse(){
        // 如果链表为空、或链表只有一个元素时，则不需要执行反转
        if (head.next == null || head.next.next == null){return;}

        // 1. 将 currentNode 定位到第一个有效结点
        LinkedNode<T> currentNode = head.next;
        LinkedNode<T> nextNode = null;
        LinkedNode<T> reversedList = null;

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

    // 逆序打印链表
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