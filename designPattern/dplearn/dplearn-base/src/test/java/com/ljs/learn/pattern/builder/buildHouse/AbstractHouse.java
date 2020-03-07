package com.ljs.learn.pattern.builder.buildHouse;

public abstract class AbstractHouse {
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void roofed();

    public void build(){
        buildBasic();
        buildWalls();
        roofed();
    }
}
