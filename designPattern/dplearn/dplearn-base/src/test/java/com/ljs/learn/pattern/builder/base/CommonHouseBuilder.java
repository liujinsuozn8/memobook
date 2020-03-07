package com.ljs.learn.pattern.builder.base;

public class CommonHouseBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("CommonHouse buildBasic");
        this.house.setBasic("basic from CommonHouse");
    }

    @Override
    public void buildWalls() {
        System.out.println("CommonHouse buildWalls");
        this.house.setWall("walls from CommonHouse");
    }

    @Override
    public void roofed() {
        System.out.println("CommonHouse roofed");
        this.house.setRoofed("roofed from CommonHouse");
    }
}
