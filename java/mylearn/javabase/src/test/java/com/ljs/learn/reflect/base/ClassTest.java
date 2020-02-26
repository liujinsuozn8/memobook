package com.ljs.learn.reflect.base;

import org.junit.Test;

// 获取Class实例的方法
public class ClassTest {

    // 方式1：调用运行时类的属性.class
    @Test
    public void test01(){
        Class clazz = Person.class;
        System.out.println(clazz);
    }

    // 方式2：通过类实例对象调用getClass()获取Class实例
    @Test
    public void test02(){
        Person p = new Person();
        Class<? extends Person> clazz = p.getClass();
        System.out.println(clazz);
    }

    // 方式3：使用Class的静态方法forName(String className)获取，className最好使用全类名
    @Test
    public void test03() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.ljs.learn.reflect.base.Person");
        System.out.println(clazz);
    }

    //判断Class对象是否相同
    @Test
    public void test04() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.ljs.learn.reflect.base.Person");
        System.out.println(clazz);

        Class clazz2 = Person.class;

        System.out.println(clazz == clazz2);
    }

    // 方式4：使用类的加载器ClassLoader
    @Test
    public void test05() throws ClassNotFoundException {
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        Class<?> clazz = classLoader.loadClass("com.ljs.learn.reflect.base.Person");

        System.out.println(clazz);
        System.out.println(clazz == Person.class);
    }

    // 可以有Class实例的类型
    @Test
    public void test06(){
        System.out.println(Class.class);
        System.out.println(void.class);
    }
}
