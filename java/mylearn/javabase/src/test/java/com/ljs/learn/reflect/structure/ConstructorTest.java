package com.ljs.learn.reflect.structure;

import org.junit.Test;

import java.lang.reflect.Constructor;

public class ConstructorTest {

    // 获取当前运行时类中的public构造器
    @Test
    public void test01(){
        Class clazz = Person.class;

        Constructor[] cons = clazz.getConstructors();
        for (Constructor con : cons) {
            System.out.println(con);
        }
    }

    // 获取当前运行时类中所有的构造器
    @Test
    public void test02(){
        Class clazz = Person.class;

        Constructor[] cons = clazz.getDeclaredConstructors();
        for (Constructor con : cons) {
            System.out.println(con);
        }
    }

    @Test
    public void test03() throws Exception{
        Class<Person> cls = Person.class;
        // 获取构造器：private Person(String name)
        Constructor<Person> con = cls.getDeclaredConstructor(String.class);
        con.setAccessible(true);

        //创建运行时类的对象
        Person testCon = con.newInstance("testCon");
        // 输出：Person{name='testCon', age=0, id=0}
        System.out.println(testCon);
    }
}
