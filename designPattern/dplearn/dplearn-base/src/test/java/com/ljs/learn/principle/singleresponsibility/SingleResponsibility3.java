package com.ljs.learn.principle.singleresponsibility;

// 客户端
public class SingleResponsibility3 {
    public static void main(String[] args) {
        Vehicle2 vehicle2 = new Vehicle2();
        vehicle2.run("汽车");
        vehicle2.runAir("飞机");
        vehicle2.runWater("船");
    }
}


// 交通工具：
// 方案3
// 方案3的优点：
//      这种修改方法没有对原来的类做大的修改，只是增加了方法
// 方案3的问题：虽然没有在类这个级别上遵守单一职责原则，只是在方法级别上，遵守了单一职责
class Vehicle2{
    public void run(String vehicle){
        System.out.println(vehicle + "正在路上行驶");
    }

    public void runAir(String vehicle){
        System.out.println(vehicle + "正在飞行");
    }

    public void runWater(String vehicle){
        System.out.println(vehicle + "正在水中行驶");
    }
}