package com.ljs.learn.pattern.builder.base;

// 指挥者 Director
public class HouseDirector {
    private HouseBuilder houseBuilder;

    // 使用构造器聚合Builder
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    // 使用getter、setter聚合Builder
    public HouseBuilder getHouseBuilder() {
        return houseBuilder;
    }

    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    // 由指挥者负责处理建造房子的流程
    public House constructHouse(){
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.roofed();

        return houseBuilder.getHouse();
    }

}
