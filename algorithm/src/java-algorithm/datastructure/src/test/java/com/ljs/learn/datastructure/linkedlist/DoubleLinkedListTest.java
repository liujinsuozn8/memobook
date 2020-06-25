package com.ljs.learn.datastructure.linkedlist;

import com.ljs.learn.datastructure.linkedlist.doublelinked.DoubleLinkedList;
import org.junit.Test;

public class DoubleLinkedListTest {
    @Test
    public void testShowList(){
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        list.add(111);
        list.add(333);
        list.add(555);

        list.showList();
    }

    @Test
    public void testUpdate(){
        DoubleLinkedList<Student> list = new DoubleLinkedList<>();
        list.add(new Student(1, "test01", 11));
        list.add(new Student(4, "test04", 14));
        list.add(new Student(2, "test02", 12));

        // 修改结点
        list.update(new Student(4, "newName", 20));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=4, name='newName', age=20}
        // Student{num=2, name='test02', age=12}
    }

    @Test
    public void testDelete(){
        DoubleLinkedList<Student> list = new DoubleLinkedList<>();
        list.add(new Student(1, "test01", 11));
        list.add(new Student(4, "test04", 14));
        list.add(new Student(2, "test02", 12));

        System.out.println("------删除前------");
        list.showList();

        System.out.println("------删除后------");
        // 删除结点
        list.delete(new Student(2, "test04", 14));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=2, name='test02', age=12}
    }

}
