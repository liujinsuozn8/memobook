package com.ljs.learn.datastructure.linkedlist.cycle;

public class Josephu {
    public static <T extends Comparable> void print(
            CycleLinkedList<T> list, int start, int step){
        LinkedNode<T> temp = list.getEnd();
        // 检查链表是否为空
        if (temp == null){
            System.out.println("null list");
            return ;
        }

        // 检查参数
        if (start < 1){
            System.out.println("error start");
            return ;
        }
        if (step < 1){
            System.out.println("error step");
            return ;
        }

        // 从end开始，遍历到 start 结点的前结点
        for (int i=1; i<start; i++){
            temp = temp.next;
        }

        // 从 start 的前结点开始遍历，直到链表中还剩一个结点 temp == temp.next
        while(temp != temp.next){
            for (int i = 1; i < step; i++){
                temp = temp.next;
            }

            // 输出结点
            System.out.println(temp.next);
            // 将结点删除
            temp.next = temp.next.next;
        }

        // 输出最后一个结点
        System.out.println(temp);
    }
}
