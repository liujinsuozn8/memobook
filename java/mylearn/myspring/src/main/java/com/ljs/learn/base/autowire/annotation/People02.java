package com.ljs.learn.base.autowire.annotation;

import javax.annotation.Resource;

public class People02 {
    //配置中有多个Dog对象，通过id进行过滤
    @Resource(name = "dog02")
    private Dog dog;

    //配置中有多个Cat对象，通过id进行过滤
    @Resource(name = "cat01")
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
