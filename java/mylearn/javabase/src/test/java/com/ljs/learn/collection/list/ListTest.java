package com.ljs.learn.collection.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
    |方法|描述|备注|
    |-|-|-|
    |void add(int index, Object del)|在index位置插入ele元素||
    |boolean addAll(int index, Collection eles)|从index位置开始将eles中的所有元素添加进来||
    |Object get(int index)|获取指定index位置的元素||
    |int indexOf(Object obj)|返回obj在集合中首次出现的位置|如果不存在则返回-1|
    |int lastIndexOf(Object obj)|返回obj在当前集合中末次出现的位置|如果不存在则返回-1|
    |Object remove(int index)|移除指定index位置的元素，并返回此元素||
    |Object set(int index, Object ele)|设置指定index位置的元素为ele||
    |List subList(int fromIndex, int toIndex)|返回从fromIndex到toIndex，但不包含toIndex位置的子集合||
   */
public class ListTest {
    @Test
    public void testAdd(){
        // |void add(int index, Object del)|在index位置插入ele元素||
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        System.out.println(list.toString()); //output：[aa, bb, cc]

        //在指定位置插入元素
        list.add(1,"xx");
        System.out.println(list.toString()); //output：[aa, xx, bb, cc]
    }

    @Test
    public void testAddAll(){
        // |boolean addAll(int index, Collection eles)|
        // 从index位置开始将eles中的所有元素添加进来||
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        System.out.println(list.toString());  //output：[aa, bb, cc]


        List list2 = Arrays.asList("fff", "eee", "hhh");
        list.addAll(1,list2);
        System.out.println(list.toString()); //output:[aa, fff, eee, hhh, bb, cc]
    }

    @Test
    public void testGet(){
        // |Object get(int index)|获取指定index位置的元素||
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        assert ( list.get(1) == "bb");
    }

    @Test
    public void testIndexOf(){
        // |int indexOf(Object obj)|返回obj在集合中首次出现的位置||

        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("bb");
        list.add("bb");
        list.add("cc");

        assert ( list.indexOf("bb") == 1 );

        // 如果不存在则返回-1
        assert ( list.indexOf("xx") == -1 );
    }

    @Test
    public void testLastIndexOf(){
        // |int lastIndexOf(Object obj)|返回obj在当前集合中末次出现的位置||

        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("bb");
        list.add("bb");
        list.add("cc");

        assert ( list.lastIndexOf("bb") == 3 );

        // 如果不存在则返回-1
        assert ( list.lastIndexOf("xx") == -1 );
    }

    @Test
    public void teestRemove(){
        // |Object remove(int index)|移除指定index位置的元素，并返回此元素||
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        Object removedNode = list.remove(1);

        assert ( removedNode == "bb" );

        assert ( list.size() == 2 );
    }

    @Test
    public void testSet(){
        // |Object set(int index, Object ele)|设置指定index位置的元素为ele||
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        list.set(1, "xxx");

        assert ( list.get(1) == "xxx");
    }

    @Test
    public void testSubList(){
        // |List subList(int fromIndex, int toIndex)|返回从fromIndex到toIndex，但不包含toIndex位置的子集合||
        List list = new ArrayList();
        list.add("aa"); //0
        list.add("bb"); //1
        list.add("cc"); //2
        list.add("dd"); //3
        list.add("ee"); //4
        list.add("ff"); //5
        list.add("gg"); //6

        List sub = list.subList(2, 4);
        System.out.println(sub.toString()); //output:[cc, dd]
    }

    @Test
    public void testListIterator(){
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        list.add("gg");

        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testListForeach(){
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        list.add("gg");

        for (Object o : list) {
            System.out.println(o);
        }
    }
    @Test
    public void testListFor(){
        List list = new ArrayList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        list.add("gg");

        for(int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }
    }

    @Test
    public void test01(){
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.remove(2);

        System.out.println(list.toString()); //output:[1, 2]
    }
}
