package com.ljs.learn.base.autowire.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class People {
    // 设置该字段可以为null，并且在@Qualifier中设置一个不存在的beanID
    @Autowired(required = false)
    @Qualifier(value = "dog03")
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
