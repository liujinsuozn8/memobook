package com.ljs.learn.autowire.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class People {

    //配置中有多个Dog对象，通过id进行过滤
    @Autowired
    @Qualifier(value = "dog01")
    private Dog dog;

    //配置中有多个Cat对象，通过id进行过滤
    @Autowired
    @Qualifier(value = "cat02")
    private Cat cat;
    private String name;

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
