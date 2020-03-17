package com.ljs.learn.pattern.composite.base;

public abstract class OrganizationComponent {
    private String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 提供默认实现，在叶子上不做重写，防止在叶子上添加管理对象
    public void add (OrganizationComponent node){
        throw new UnsupportedOperationException();
    }

    // 提供默认实现，在叶子上不做重写，防止在叶子上添加管理对象
    public void remove(OrganizationComponent node){
        throw new UnsupportedOperationException();
    }

    public abstract void print();
}
