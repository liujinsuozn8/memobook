package com.ljs.learn.reflect.structure;

import org.junit.Test;

public class InterfaceTest {

    @Test
    public void test01(){
        Class clazz = Person.class;
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println(anInterface);
        }

        //获取父类的接口
        Class<?>[] interfaces2 = clazz.getSuperclass().getInterfaces();
        for (Class<?> aClass : interfaces2) {
            System.out.println(aClass.getName());
        }

        //output：
        // interface java.lang.Comparable
        // interface com.ljs.learn.reflect.structure.MyInterface
        // java.io.Serializable
    }
}
