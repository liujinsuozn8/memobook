package com.ljs.learn.collection.set;

public class NoHashAndEquals {
    private String name;
    private int age;

    public NoHashAndEquals(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "NoHashAndEquals{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
