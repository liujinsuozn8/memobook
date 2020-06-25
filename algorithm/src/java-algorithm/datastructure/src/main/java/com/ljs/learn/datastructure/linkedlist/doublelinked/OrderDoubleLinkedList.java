package com.ljs.learn.datastructure.linkedlist.doublelinked;

public class OrderDoubleLinkedList<T extends Comparable> {
    private OrderDoubleLinkedNode<T> head = new OrderDoubleLinkedNode<>(null);

    // 添加元素
    public void add(T data){
        OrderDoubleLinkedNode<T> temp = head;

        // 遍历链表，找到最后一个结点
        while (temp.next != null){
            // 如果后结点比新结点大，则将新结点添加到后结点前面，保持链表的升序特性
            if (temp.next.compareTo(data) > 0){
                break;
            }
            temp = temp.next;
        }

        // 将结点添加到链表中
        OrderDoubleLinkedNode<T> node = new OrderDoubleLinkedNode<>(data);

        // 建立新结点与后结点的关系
        node.next = temp.next;
        if (temp.next != null){
            temp.next.prev = node;
        }

        // 建立前结点与新结点的关系
        temp.next = node;
        node.prev = temp;
    }

    // 遍历链表
    public void showList(){
        if (head.next == null) {
            System.out.println("null list");
            return;
        }

        OrderDoubleLinkedNode<T> temp = head.next;
        while (temp != null){
            System.out.println(temp);
            temp = temp.next;
        }
    }

    // 更新元素
    public void update(T data){
        if (head.next == null){ return; }
        OrderDoubleLinkedNode<T> temp = head.next;

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

        OrderDoubleLinkedNode<T> temp = head.next;
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

class OrderDoubleLinkedNode<T extends Comparable> implements Comparable{
    T data;
    OrderDoubleLinkedNode<T> prev;
    OrderDoubleLinkedNode<T> next;

    public OrderDoubleLinkedNode(T data) {
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