package com.ljs.learn.pattern.builder.base;

// 抽象建造者 Builder
public abstract class HouseBuilder {
    protected House house = new House();

    // 构建流程
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void roofed();

    // 获取产品
    public House getHouse (){
        return house;
    }
}
