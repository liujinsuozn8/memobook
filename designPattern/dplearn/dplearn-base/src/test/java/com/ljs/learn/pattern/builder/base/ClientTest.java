package com.ljs.learn.pattern.builder.base;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建普通房子
        CommonHouseBuilder commonBuilder = new CommonHouseBuilder();
        // 准备创建房子的指挥者
        HouseDirector houseDirector = new HouseDirector(commonBuilder);
        // 创建并返回产品对象
        House house01 = houseDirector.constructHouse();

        System.out.println(house01);

        // 创建高楼
        HightBuildingBuilder hightBuilder = new HightBuildingBuilder();
        // 重置指挥者
        houseDirector.setHouseBuilder(hightBuilder);
        House house02 = houseDirector.constructHouse();
        System.out.println(house02);
    }
}
