package com.ljs.learn.pattern.bridge.base;

// 折叠手机类
public class FoldedPhone extends Phone {
    public FoldedPhone(Brand brand) {
        super(brand);
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("FoldedPhone open");
    }

    @Override
    protected void close() {
        super.close();
        System.out.println("FoldedPhone close");
    }

    @Override
    protected void call() {
        super.call();
        System.out.println("FoldedPhone call");
    }
}
