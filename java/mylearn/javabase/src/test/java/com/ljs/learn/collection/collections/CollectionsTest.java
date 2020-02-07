package com.ljs.learn.collection.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {
    @Test
    public void testReverse(){
        // |排序操作|reverse(List)|反转 List 中元素的顺序||
        List list = new ArrayList();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(6);
        list.add(77);
        list.add(88);

        System.out.println(list);
        //output:
        //[111, 222, 333, 6, 77, 88]

        Collections.reverse(list);
        System.out.println(list);
        //output:
        //[88, 77, 6, 333, 222, 111]
    }

    @Test
    public void testShuffle() {
        // |排序操作|shuffle(List)|对 List 集合元素进行随机排序||
        List list = new ArrayList();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(6);
        list.add(77);
        list.add(88);
        System.out.println(list);

        Collections.shuffle(list);
        System.out.println(list);
    }

    @Test
    public void testSortByComparable(){
        // |排序操作|sort(List)|根据元素的自然顺序对指定 List 集合元素按升序排序||
        List list = new ArrayList();
        list.add(111);
        list.add(222);
        list.add(333);
        list.add(6);
        list.add(77);
        list.add(88);
        System.out.println(list);
        //output:
        //[111, 222, 333, 6, 77, 88]

        Collections.sort(list);
        System.out.println(list);
        //output:
        //[6, 77, 88, 111, 222, 333]
    }

    @Test
    public void testFrequency(){
        // |查找、替换|int frequency(Collection，Object)|
        // 返回指定集合中指定元素的出现次数||

        List list = new ArrayList();
        list.add(111);
        list.add(222);
        list.add(111);
        list.add(6);
        list.add(111);
        list.add(88);
        System.out.println(list);

        int frequency = Collections.frequency(list, 111);
        assert (frequency == 3);
    }

    @Test
    public void testCopy(){
        // |查找、替换|void copy(List dest,List src)|
        // 将src中的内容复制到dest中||

        List list = new ArrayList();
        list.add(111);
        list.add(222);
        list.add(6);
        list.add(88);
        System.out.println(list);

        // 异常写法
        // List dest = new ArrayList();
        // Collections.copy(dest, list);

        //通过一个长度与list相等的数组来创建dest集合
        List dest = Arrays.asList(new Object[list.size()]);
        System.out.println(dest);
        //output:
        // [null, null, null, null]

        //拷贝集合
        Collections.copy(dest, list);
        System.out.println(dest);
        //output:
        // [111, 222, 6, 88]
    }
}
