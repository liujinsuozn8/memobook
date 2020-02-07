package com.ljs.learn.collection.set;

import java.util.Objects;

public class WithHashAndEquals {
    private String name;
    private int age;

    public WithHashAndEquals(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithHashAndEquals that = (WithHashAndEquals) o;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "WithHashAndEquals{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
