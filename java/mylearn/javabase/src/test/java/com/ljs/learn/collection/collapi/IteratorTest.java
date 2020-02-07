package com.ljs.learn.collection.collapi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/*
* 使用Iterator接口来实现集合元素的遍历
* */
public class IteratorTest {

    //使用迭代器遍历集合
    @Test
    public void testIterator(){
        Collection coll = new ArrayList();

        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        Iterator iterator = coll.iterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    //迭代时，删除某个元素
    @Test
    public void testRemove(){
        Collection coll = new ArrayList();

        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        Iterator iterator = coll.iterator();

        while (iterator.hasNext()){
            Object obj = iterator.next();
            //删除某个元素
            if ("BB".equals(obj)){
                iterator.remove();
                // 再次调用会导致IllegalStateException异常
                // 因为当前指针没有指向一个有效的元素
                // iterator.remove();
            }
        }

        // 重新遍历并打印
        iterator = coll.iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next());

        }
    }

}
