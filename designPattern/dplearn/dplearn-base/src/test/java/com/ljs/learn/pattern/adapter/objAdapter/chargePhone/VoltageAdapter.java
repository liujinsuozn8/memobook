package com.ljs.learn.pattern.adapter.objAdapter.chargePhone;

// 适配器
public class VoltageAdapter implements IVoltagge5V {
    private Voltage220V voltage220V;

    public VoltageAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    @Override
    public int output5V() {
        // 获取被适配对象的电压
        int src = voltage220V.output220V();
        //将电压转换为5伏
        int dst = src/44;

        System.out.println("Adapter = " + dst + "V");
        return dst;
    }
}
