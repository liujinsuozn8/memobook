package com.ljs.learn.collection.set;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SetTest {
    @Test
    public void testAdd(){
        Set set = new HashSet();
        set.add("aa");
        set.add("aa");
        set.add(1234);
        set.add("bb");
        set.add(new Date());
        set.add("cc");

        for (Object o : set) {
            System.out.println(o);
        }
    }

    // 测试重复插入重写过hashCode和equals方法的类对象
    @Test
    public void testddWithHashAndEquals(){
        HashSet<Object> set = new HashSet<>();
        set.add(new WithHashAndEquals("aa", 11));
        set.add(new WithHashAndEquals("aa", 11));

        System.out.println(set.toString());
        // output:[WithHashAndEquals{name='aa', age=11}]
    }
    // 测试重复插入没有重写hashCode和equals方法的类对象
    @Test
    public void testddNoHashAndEquals(){
        HashSet<Object> set = new HashSet<>();
        set.add(new NoHashAndEquals("aa", 11));
        set.add(new NoHashAndEquals("aa", 11));

        System.out.println(set.toString());
        // output:
        // [NoHashAndEquals{name='aa', age=11}, NoHashAndEquals{name='aa', age=11}]
    }

    // 测试HashSet的底层存储结构和存储步骤
    // 1. 存储了元素：aa，bb，set=[aa，bb]
    // 2. 将aa 更新为 cc，set=[cc，bb]
    // 3. 将cc从set中删除
    //      - cc存储在hashCode(aa)的位置，删除时使用的是hashCode(cc)来判断元素是否存在，所以无法删除
    //      - set=[cc，bb]
    // 4. 将另一个cc添加到set中
    //      - set中，旧的cc保存在hashCode(aa)处，hashCode(cc)没有元素，可以添加
    //      - set=[cc，bb，cc]
    // 5. 插入新的aa
    //      - hashCode(aa)处有值，然后使用equals比较
    //      - 当前hashCode(aa)处保存的是cc，所以两个元素不同，可以插入
    //      - set=[(cc，aa)，bb，cc]
    //
    // output：
    // output1：[User{name='bb', age=22}, User{name='aa', age=11}]
    // output2：[User{name='bb', age=22}, User{name='cc', age=11}]
    // output3：[User{name='bb', age=22}, User{name='cc', age=11}, User{name='cc', age=11}]
    // output4：[User{name='bb', age=22}, User{name='cc', age=11}, User{name='cc', age=11}, User{name='aa', age=11}]

    @Test
    public void test01(){
        Set set = new HashSet();

        User u1 = new User("aa", 11);
        User u2 = new User("bb", 22);
        set.add(u1);
        set.add(u2);

        System.out.println(set); //output1

        u1.setName("cc");
        set.remove(u1);
        System.out.println(set); //output2

        set.add(new User("cc", 11));
        System.out.println(set); //output3

        set.add(new User("aa", 11));
        System.out.println(set); //output4
    }
}
