package com.ljs.learn.principle.singleresponsibility;

// 客户端
public class SingleResponsibility2 {
    public static void main(String[] args) {
        RoadVehicle roadVehicle = new RoadVehicle();
        roadVehicle.run("摩托车");//摩托车正在路上行驶

        AirVehicle airVehicle = new AirVehicle();
        airVehicle.run("飞机");//飞机正在飞行

        WaterVehicle waterVehicle = new WaterVehicle();
        waterVehicle.run("船"); //船正在水中行驶
    }
}


// 交通工具：
// 方案2
// 方案2的优点：不同的类负责不同的功能，遵守了单一职责原则，
// 方案2的问题：在方案1的基础上做了非常大的改动，同时修改了类和客户端
// 解决方案：直接修改Vehicle类，改动的代码会比较少 --> 方案3

class RoadVehicle{
    public void run(String vehicle){
        System.out.println(vehicle + "正在路上行驶");
    }
}

class AirVehicle{
    public void run(String vehicle){
        System.out.println(vehicle + "正在飞行");
    }
}

class WaterVehicle{
    public void run(String vehicle){
        System.out.println(vehicle + "正在水中行驶");
    }
}