package com.ljs.learn.principle.singleresponsibility;

// 客户端
public class SingleResponsibility1 {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("摩托车");//摩托车正在路上行驶
        vehicle.run("汽车"); //汽车正在路上行驶
        vehicle.run("飞机"); //打印：飞机正在路上行驶，不符合常识
    }
}

// 交通工具
// 方案1
// 方案1的问题：在run方法中，违反了单一职责原则，所有类型的交通工具共享功能，导致了不符合常识的打印内容
// 解决方案：根据交通工具的运行方法，分解成不同类 --> 方案2
class Vehicle{
    public void run(String vehicle){
        System.out.println(vehicle + "正在路上行驶");
    }
}