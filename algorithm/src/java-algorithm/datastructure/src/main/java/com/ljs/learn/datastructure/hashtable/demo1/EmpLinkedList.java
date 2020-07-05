package com.ljs.learn.datastructure.hashtable.demo1;

// 员工信息链表
public class EmpLinkedList {
    private Employee head;

    // 向链表中添加或修改元素
    public void add(Employee node) {
        if (head == null) {
            head = node;
        } else {
            Employee temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = node;
        }
    }

    // 遍历输出链表
    public void list() {
        if (head == null) return;
        Employee temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    // 获取元素
    public Employee get(int id) {
        if (head == null) return null;
        Employee temp = head;
        while (temp != null) {
            if (id == temp.id) {
                return temp;
            }
        }

        // 如果没有找到，则返回null
        return null;
    }
}
