package com.ljs.learn.pattern.builder.buildHouse;

public class CommonHose extends AbstractHouse {
    @Override
    public void buildBasic() {
        System.out.println("CommonHose buildBasic");
    }

    @Override
    public void buildWalls() {
        System.out.println("CommonHose buildWalls");
    }

    @Override
    public void roofed() {
        System.out.println("CommonHose roofed");
    }
}
