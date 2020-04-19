package com.ljs.learn.base.annotation.base;

public class JDKBaseAnnotationTest {
}


class Student extends Person{
    // 通过注解表明属性：id没有被使用
    @SuppressWarnings("unused")
    public String id;

    @Override
    public void walk() {
        super.walk();
    }

    // walk 与 wa1k 及其相似，如果不加@Override很难分辨出是否正确重写了方法
    // @Override
    public void wa1k(){
        super.walk();
    }

    //可以不添加：@Override
    public void speak() {
        super.speak();
    }
}

class Person{
    private  String name;
    private int age;

    public Person() {
    }

    public void walk(){
        System.out.println("walk");
    }

    public void speak(){
        System.out.println("speak");
    }
}
