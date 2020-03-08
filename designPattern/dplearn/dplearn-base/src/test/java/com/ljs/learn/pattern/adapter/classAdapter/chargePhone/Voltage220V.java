package com.ljs.learn.pattern.adapter.classAdapter.chargePhone;

// 被适配者
public class Voltage220V {
    public int output220V(){
        int src = 220;
        System.out.println("Voltage220V output");
        return src;
    }
}
