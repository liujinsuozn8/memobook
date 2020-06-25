package com.ljs.learn.datastructure.linkedlist;

import com.ljs.learn.datastructure.linkedlist.cycle.CycleLinkedList;
import org.junit.Test;

public class CycleLinkedListTest {
    @Test
    public void testCycleLinkedList(){
        CycleLinkedList<Integer> list = new CycleLinkedList<>();
        System.out.println("-------");
        list.showList();
        System.out.println("-------");
        list.add(123);
        list.showList();

        System.out.println("-------");
        list.add(234);
        list.add(345);
        list.add(456);
        list.showList();
    }
}
