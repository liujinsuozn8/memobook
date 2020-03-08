package com.ljs.learn.pattern.adapter.classAdapter.chargePhone;

// 适配器
public class VoltageAdapter extends Voltage220V implements IVoltagge5V {
    @Override
    public int output5V() {
        // 获取被适配对象的电压
        int src = output220V();
        //将电压转换为5伏
        int dst = src/44;

        System.out.println("Adapter = " + dst + "V");
        return dst;
    }
}
