package com.ljs.learn.pattern.builder.base;

public class HightBuildingBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("HightBuilding buildBasic");
        this.house.setBasic("basic from HightBuilding");
    }

    @Override
    public void buildWalls() {
        System.out.println("HightBuilding buildWalls");
        this.house.setWall("walls from HightBuilding");
    }

    @Override
    public void roofed() {
        System.out.println("HightBuilding roofed");
        this.house.setRoofed("roofed from HightBuilding");
    }
}
