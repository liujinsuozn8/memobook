package com.ljs.learn.collection.map;

import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapTest {

    @Test
    public void testComparable(){
        Map map = new TreeMap();

        map.put(new User("sss", 11), 1);
        map.put(new User("aaa", 22), 2);
        map.put(new User("sww", 33), 3);
        map.put(new User("bbb", 44), 4);

        // 使用自然排序
        Set entrySet = map.entrySet();
        for (Object o : entrySet) {
            System.out.println(o);
        }

        // output:
        // User{name='aaa', age=22}=2
        // User{name='bbb', age=44}=4
        // User{name='sss', age=11}=1
        // User{name='sww', age=33}=3
    }

    @Test
    public void testComparator(){
        Comparator<User> comparator = (User u1, User u2) -> Integer.compare(u1.getAge(), u2.getAge());
        Map map = new TreeMap(comparator);

        map.put(new User("sss", 22), 1);
        map.put(new User("aaa", 11), 2);
        map.put(new User("sww", 5), 3);
        map.put(new User("bbb", 3), 4);
        map.put(new User("ddd", 3), 4);


        // 使用定制排序
        Set entrySet = map.entrySet();
        for (Object o : entrySet) {
            System.out.println(o);
        }

        // output:
        // User{name='bbb', age=3}=4
        // User{name='sww', age=5}=3
        // User{name='aaa', age=11}=2
        // User{name='sss', age=22}=1
    }
}
