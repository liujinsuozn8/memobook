package com.ljs.learn.pattern.composite.base;

public class Department extends OrganizationComponent {
    public Department(String name) {
        super(name);
    }

    @Override
    public void print() {
        System.out.println(getName());
    }
}
