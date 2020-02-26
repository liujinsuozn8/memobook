package com.ljs.learn.reflect.structure;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// 获取当前运行时类的属性结构
public class FieldTest {
    // getFields， 获取当前运行时类及其父类中声明为public的属性
    @Test
    public void test01(){
        Class clazz = Person.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    // getDeclaredFields， 返回当前运行时类中所有声明过的属性(所有权限)，不包括父类中的属性
    @Test
    public void test02(){
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
    }

    // 获取当前Class中每个属性的权限修饰符、数据类型、变量名
    @Test
    public void test03(){
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 1. 获取权限修饰符
            int modifier = field.getModifiers();
            System.out.print(Modifier.toString(modifier) + "\t");

            // 2. 获取数据类型
            Class type = field.getType();
            System.out.print(type + "\t");

            // 3. 获取变量名
            String name = field.getName();
            System.out.println(name);

            System.out.println();
        }
    }

    // 设置public属性（不常用，因为`getField()`只能获取到public属性）
    @Test
    public void test04() throws Exception {
        Class clazz = Person.class;

        // 创建类的对象
        Person p = (Person) clazz.newInstance();

        // 获取指定属性:需要属性声明为public
        Field id = clazz.getField("id");

        // 设置属性的值
        id.set(p, 1234);

        // 获取属性的值
        int newid = (int) id.get(p);

        System.out.println(newid);
        // output：1234
    }

    //操作当前运行时类的指定属性
    @Test
    public void test05() throws Exception{
        Class<Person> cls = Person.class;

        Person p = cls.newInstance();

        //获取指定变量名属性
        Field name = cls.getDeclaredField("name");
        //使当前变量是可用、可访问的
        name.setAccessible(true);
        // set(需要设定属性的对象, 属性值)
        name.set(p, "testname");
        // get(需要获取属性值的对象)
        String pname = (String) name.get(p);
        System.out.println(pname);// 输出：testname

        //操作静态属性
        Field fieldX = cls.getDeclaredField("X");
        fieldX.setAccessible(true);
        int staticX = (int) fieldX.get(Person.class);
        //int staticX = (int) fieldX.get(null);// 静态变量时，对象可以为null
        System.out.println(staticX); // 输出：10

        fieldX.set(Person.class, 50);
        staticX = (int) fieldX.get(Person.class);
        //staticX = (int) fieldX.get(null);
        System.out.println(staticX); // 输出：50
    }
}
