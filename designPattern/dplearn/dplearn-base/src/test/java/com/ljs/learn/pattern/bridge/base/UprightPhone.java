package com.ljs.learn.pattern.bridge.base;

// 直立式手机类
public class UprightPhone extends Phone {
    public UprightPhone(Brand brand) {
        super(brand);
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("UprightPhone open");
    }

    @Override
    protected void close() {
        super.close();
        System.out.println("UprightPhone close");
    }

    @Override
    protected void call() {
        super.call();
        System.out.println("UprightPhone call");
    }
}
