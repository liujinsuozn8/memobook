package com.ljs.learn.reflect.structure;

@MyAnnotation(value="personclass")
public class Person extends Creature<String> implements Comparable<String>, MyInterface{

    public static int X=10;

    private String name;
    int age;
    public int id;

    public Person() {
    }

    @MyAnnotation(value="abc")
    private Person(String name) {
        this.name = name;
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @MyAnnotation
    private String show(String nation) throws NullPointerException, ClassCastException{
        System.out.println("nation = " + nation);
        return nation;
    }

    public String display(String interest){
        return interest;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    @Override
    public void info() {
        System.out.println("this is a Person");
    }

    private static void showDesc(){
        System.out.println("this is showDesc");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}