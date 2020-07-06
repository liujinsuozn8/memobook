package com.ljs.learn.myspringannotation.regist.scope;

public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        System.out.println("construct student, name="+name + ", age=" + age);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
