package com.ljs.learn.datastructure.linkedlist;

import com.ljs.learn.datastructure.linkedlist.doublelinked.DoubleLinkedList;
import com.ljs.learn.datastructure.linkedlist.doublelinked.OrderDoubleLinkedList;
import org.junit.Test;

public class OrderDoubleLinkedListTest {
    @Test
    public void testOrderDoubleLinkedList(){
        OrderDoubleLinkedList<Integer> list = new OrderDoubleLinkedList<>();
        list.add(222);
        list.add(111);
        list.add(444);
        list.add(333);

        list.showList();
        // 输出
        // 111
        // 222
        // 333
        // 444
    }
}
