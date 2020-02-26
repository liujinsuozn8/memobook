package com.ljs.learn.reflect.base;

import org.junit.Test;

import java.lang.reflect.Constructor;

//通过反射创建运行时类的对象
public class NewInstanceTest {

    // 通过newInstance()创建对应的运行时类的对象，内部调用了类的空参构造器
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class clazz = Person.class;
        Object obj = clazz.newInstance();
        // Person{name='null', age=0}
        System.out.println(obj);
    }

    @Test
    public void test2() throws Exception {
        //获取Class对象
        Class<Person> cls = Person.class;
        //获取构造器实例
        Constructor<Person> cons = cls.getConstructor(String.class, int.class);
        //通过构造器实例化对象
        Person aa = cons.newInstance("aa",12);
        System.out.println(aa);
    }
}
