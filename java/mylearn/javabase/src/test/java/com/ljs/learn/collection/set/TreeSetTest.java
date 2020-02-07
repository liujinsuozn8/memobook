package com.ljs.learn.collection.set;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {
    // TreeSet的插入测试:插入基本类型
    @Test
    public void testAdd(){
        Set tree = new TreeSet();

        // TreeSet只能添加相同类型的元素
        // tree.add(123);
        // tree.add("qwer");
        // tree.add("cvxv");

        tree.add("123");
        tree.add("qwer");
        tree.add("cvxv");
    }

    // TreeSet的有序性测试
    @Test
    public void testItetator(){
        Set tree = new TreeSet();

        tree.add("123");
        tree.add("qwer");
        tree.add("cvxv");

        Iterator iterator = tree.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        // output:
        // 123
        // cvxv
        // qwer

        Set tree2 = new TreeSet();
        tree2.add(100);
        tree2.add(12);
        tree2.add(20);
        tree2.add(5);
        for (Object o : tree2) {
            System.out.println(o);
        }

        // 5
        // 12
        // 20
        // 100
    }

    // TreeSet的插入测试：插入自定义类型
    @Test
    public void testAddUser(){
        Set tree = new TreeSet();

        // User的compareTo只对name进行了排序
        // 虽然age不同，但是compareTo的结果都是0，
        // 所以第二个和第三个被认为和第一个一样，无法添加
        tree.add(new User("aaa", 111));
        tree.add(new User("aaa", 222));
        tree.add(new User("aaa", 333));

        for (Object o : tree) {
            System.out.println(o);
        }

        // output:
        // User{name='aaa', age=111}
    }

    // TreeSet的自然排序测试
    @Test
    public void testSort(){
        Set tree = new TreeSet();
        tree.add(new User("ccc", 11));
        tree.add(new User("abb", 13));
        tree.add(new User("eee", 11));
        tree.add(new User("aaa", 11));

        for (Object o : tree) {
            System.out.println(o);
        }

        // output:
        // User{name='aaa', age=11}
        // User{name='abb', age=13}
        // User{name='ccc', age=11}
        // User{name='eee', age=11}
    }

    // TreeSet的定制排序测试
    @Test
    public void testSort2(){
        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof User && o2 instanceof User) {
                    User u1 = (User) o1;
                    User u2 = (User) o2;
                    return u1.getName().compareTo(u2.getName());
                } else {
                    throw new RuntimeException("error type");
                }
            }
        };

        Set tree = new TreeSet(comparator);
        tree.add(new User("ccc", 11));
        tree.add(new User("abb", 13));
        tree.add(new User("eee", 11));
        tree.add(new User("aaa", 11));

        for (Object o : tree) {
            System.out.println(o);
        }

        // output:
        // User{name='aaa', age=11}
        // User{name='abb', age=13}
        // User{name='ccc', age=11}
        // User{name='eee', age=11}
    }
}
