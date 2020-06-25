package com.ljs.learn.datastructure.linkedlist;

public class Student implements Comparable {
    private int num;
    private String name;
    private int age;

    public Student(int num, String name, int age) {
        this.num = num;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    @Override
    public int compareTo(Object o) {
        int compareNum = ((Student)o).getNum();
        return num - compareNum;
    }
}
