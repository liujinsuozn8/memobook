package com.ljs.learn.datastructure.linkedlist;

import com.ljs.learn.datastructure.linkedlist.single.base.BaseSingleLinkedList;
import com.ljs.learn.datastructure.linkedlist.single.base.OrderSingleLinkedList;
import com.ljs.learn.datastructure.linkedlist.single.forproblem.BaseSingleLinkedList02;
import com.ljs.learn.datastructure.linkedlist.single.forproblem.OrderSingleLinkedList02;
import org.junit.Test;

public class SingleLinkedListTest {
    // 测试单链表
    @Test
    public void test01(){
        // 1. 测试添加和遍历
        BaseSingleLinkedList<Student> list = new BaseSingleLinkedList<>();
        list.add(new Student(1, "test01", 11));
        list.add(new Student(4, "test04", 14));
        list.add(new Student(2, "test02", 12));

        list.showList();
        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=4, name='test04', age=14}
        // Student{num=2, name='test02', age=12}

        // 2. 测试修改结点
        list.update(new Student(4, "newName", 20));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=4, name='newName', age=20}
        // Student{num=2, name='test02', age=12}

        // 3. 测试删除结点
        list.delete(new Student(4, "newName", 20));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=2, name='test02', age=12}
    }

    // 测试有序单链表
    @Test
    public void test02(){
        // 1. 测试添加和遍历
        OrderSingleLinkedList<Student> list = new OrderSingleLinkedList<>();
        list.add(new Student(1, "test01", 11));
        list.add(new Student(4, "test04", 14));
        list.add(new Student(2, "test02", 12));

        list.showList();
        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=4, name='test04', age=14}
        // Student{num=2, name='test02', age=12}

        // 2. 测试修改结点
        list.update(new Student(4, "newName", 20));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=4, name='newName', age=20}
        // Student{num=2, name='test02', age=12}

        // 3. 测试删除结点
        list.delete(new Student(4, "newName", 20));
        list.showList();

        // 输出
        // Student{num=1, name='test01', age=11}
        // Student{num=2, name='test02', age=12}
    }

    // 测试获取单链表的有效结点个数
    @Test
    public void testLength(){
        BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
        list.add(111);
        list.add(222);
        list.add(333);

        assert (list.length() == 3);

        OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
        list2.add(111);
        list2.add(222);
        list2.add(333);

        assert (list2.length() == 3);
    }

    // 测试获取单链表的倒数第index个结点
    @Test
    public void testFindLastIndexNode(){
        BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(444);

        assert(list.findLastIndexNode(1) == 444);
        assert(list.findLastIndexNode(4) == 111);
        assert(list.findLastIndexNode(0) == null);

        OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
        list2.add(111);
        list2.add(222);
        list2.add(333);
        list2.add(444);

        assert(list2.findLastIndexNode(1) == 444);
        assert(list2.findLastIndexNode(4) == 111);
        assert(list2.findLastIndexNode(0) == null);
    }

    // 测试获取单链表的倒数第index个结点
    @Test
    public void testReverse(){
        BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(444);

        list.showList();
        System.out.println("---------------");

        list.reverse();
        list.showList();
        // 输出
        // 444 、333 、222 、111
        System.out.println("---------------");

        OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
        list2.add(111);
        list2.add(222);
        list2.add(333);
        list2.add(444);

        list2.showList();
        System.out.println("---------------");

        list2.reverse();
        list2.showList();
        // 输出
        // 444 、333 、222 、111
    }

    // 测试获取单链表的倒数第index个结点
    @Test
    public void testReverseShowList(){
        BaseSingleLinkedList02<Integer> list = new BaseSingleLinkedList02<>();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(444);

        System.out.println("-------逆序输出--------");
        list.reverseShowList();
        // 输出
        // 444 、333 、222 、111
        System.out.println("-------正常输出--------");
        list.showList();

        OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
        list2.add(111);
        list2.add(222);
        list2.add(333);
        list2.add(444);

        System.out.println("-------逆序输出--------");
        list2.reverseShowList();
        // 输出
        // 444 、333 、222 、111
        System.out.println("-------正常输出--------");
        list2.showList();
    }

    @Test
    public void testMerge(){
        OrderSingleLinkedList02<Integer> list = new OrderSingleLinkedList02<>();
        list.add(222);
        list.add(444);

        OrderSingleLinkedList02<Integer> list2 = new OrderSingleLinkedList02<>();
        list2.add(111);
        list2.add(333);
        list2.add(666);

        list.merge(list2);

        list.showList();
        // 输出
        // 111 、222 、333 、444 、666
    }
}
