package com.ljs.learn.reflect.structure;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParentTest {

    // 获取运行时类的父类
    @Test
    public void test01(){
        Class clazz = Person.class;

        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
    }

    // 获取运行时类带泛型的父类
    @Test
    public void tests02(){
        Class clazz = Person.class;
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
    }

    // 获取运行时类带泛型的父类泛型
    @Test
    public void tests03(){
        Class clazz = Person.class;
        Type genericSuperclass = clazz.getGenericSuperclass();
        // 转换为带参数的类型
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        // 获取类型中的参数，即获取泛型
        Type[] actualTypeArguments = paramType.getActualTypeArguments();

        // 获取泛型的类型
        System.out.println(actualTypeArguments[0].getTypeName());

        // 泛型本质上也是运行时类，可以转换成Class，来直接获取类型名
        System.out.println(((Class) actualTypeArguments[0]).getName());
    }
}
