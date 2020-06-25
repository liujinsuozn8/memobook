package com.ljs.learn.datastructure.linkedlist;

import com.ljs.learn.datastructure.linkedlist.cycle.CycleLinkedList;
import com.ljs.learn.datastructure.linkedlist.cycle.Josephu;
import org.junit.Test;

public class JosephuTest {
    @Test
    public void test01(){
        CycleLinkedList<Integer> list = new CycleLinkedList<>();
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);
        list.add(55);

        Josephu.print(list, 1, 2);
        // 输出
        // 22 、44 、11 、55 、33
    }
}
