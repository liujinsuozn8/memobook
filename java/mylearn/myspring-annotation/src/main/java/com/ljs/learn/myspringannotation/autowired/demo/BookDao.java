package com.ljs.learn.myspringannotation.autowired.demo;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "name='" + name + '\'' +
                '}';
    }
}
