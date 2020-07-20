package com.ljs.learn.myspringannotation.propertyassign;

import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class Person {
    // 1. 使用普通的数据设置属性值
    @Value("testName")
    private String name;

    // 2. 通过SpEL设置属性值
    @Value("#{22-2}")
    private Integer age;

    // 3. 使用`${}`， 从配置文件中读取数据，即从运行环境变量中获取值
    @Value("${address}")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(age, person.age) &&
                Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, address);
    }
}
