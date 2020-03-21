package com.ljs.learn.autowire.annotation;

import org.springframework.lang.Nullable;

public class Cat {
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
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
