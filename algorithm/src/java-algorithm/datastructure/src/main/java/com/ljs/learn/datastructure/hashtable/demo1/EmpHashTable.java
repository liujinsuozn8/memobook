package com.ljs.learn.datastructure.hashtable.demo1;

// 哈希表
public class EmpHashTable {
    // 数组的最大长度
    private int size;
    // 保存链表的数组
    private EmpLinkedList[] rows;

    public EmpHashTable(int size) {
        this.size = size;
        rows = new EmpLinkedList[size];
        // 创建数组后，初始化每个链表
        for (int i = 0; i < size; i++) {
            rows[i] = new EmpLinkedList();
        }
    }

    // 提供一个hash函数，来将id映射为数组索引
    private int hash(int id) {
        return id % size;
    }

    // 添加元素
    public void add(Employee node) {
        // 确定添加到哪条链表中
        int idx = hash(node.id);
        // 执行添加
        rows[idx].add(node);
    }

    // 遍历元素，遍历每个链表
    public void list(){
        for (EmpLinkedList row : rows) {
            row.list();
        }
    }

    // 获取元素，如果没有找到则返回 null
    public Employee get(int id){
        // 确定元素在哪条链表中
        int idx = hash(id);
        // 从链表中获取元素
        return rows[idx].get(id);
    }
}
