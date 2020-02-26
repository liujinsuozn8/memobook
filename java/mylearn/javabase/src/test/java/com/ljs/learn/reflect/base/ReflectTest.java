package com.ljs.learn.reflect.base;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {

    @Test
    public void test01() throws Exception {
        // 1.通过反射创建Person对象
        Class clazz = Person.class;
        Constructor cons = clazz.getConstructor(String.class, int.class);
        Object obj = cons.newInstance("TestPserson", 11);
        Person p = (Person) obj;
        System.out.println(p.toString());

        // 2.通过反射，调用对象指定的属性、方法
        // 调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(p, 20);
        System.out.println(p.toString());

        // 调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(p);

        // 3.调用私有结构
        // 调用私有构造
        Constructor cons2 = clazz.getDeclaredConstructor(String.class);
        cons2.setAccessible(true);
        Person p2  = (Person) cons2.newInstance("PrivatePerson");
        System.out.println(p2.toString());

        // 调用属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p, "ChangeTest");
        System.out.println(p.toString());
    }
}