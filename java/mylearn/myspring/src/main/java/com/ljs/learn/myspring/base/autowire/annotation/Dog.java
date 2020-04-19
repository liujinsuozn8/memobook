package com.ljs.learn.myspring.base.autowire.annotation;

import org.springframework.lang.Nullable;

public class Dog {
    @Nullable
    private String name;

    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}
