package com.ljs.learn.datastructure.linkedlist.doublelinked;

public class DoubleLinkedList<T extends Comparable> {
    private DoubleLinkedNode<T> head = new DoubleLinkedNode<>(null);

    // 添加元素
    public void add(T data){
        DoubleLinkedNode<T> temp = head;

        // 遍历链表，找到最后一个结点
        while (temp.next != null){
            temp = temp.next;
        }

        // 将结点添加到链表末尾
        DoubleLinkedNode<T> node = new DoubleLinkedNode<>(data);
        temp.next = node;
        node.prev = temp;
    }

    // 遍历链表
    public void showList(){
        if (head.next == null) {
            System.out.println("null list");
            return;
        }

        DoubleLinkedNode<T> temp = head.next;
        while (temp != null){
            System.out.println(temp);
            temp = temp.next;
        }
    }

    // 更新元素
    public void update(T data){
        if (head.next == null){ return; }
        DoubleLinkedNode<T> temp = head.next;

        while (temp != null){
            if (temp.compareTo(data) == 0){
                temp.data = data;
                break;
            }

            temp = temp.next;
        }
    }

    // 删除结点
    public void delete(T data){
        if (head.next == null){ return; }

        DoubleLinkedNode<T> temp = head.next;
        while (temp != null){
            if (temp.compareTo(data) == 0){
                // 连接目标结点的前结点和后结点
                temp.prev.next = temp.next;

                // 如果当前temp结点是最后一个结点，则不执行，防止空指针异常
                if (temp.next != null){
                    temp.next.prev = temp.prev;
                }
                break;
            }

            temp = temp.next;
        }
    }

}

class DoubleLinkedNode<T extends Comparable> implements Comparable{
    T data;
    DoubleLinkedNode<T> prev;
    DoubleLinkedNode<T> next;

    public DoubleLinkedNode(T data) {
        this.data = data;
        next = null;
        prev = null;
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