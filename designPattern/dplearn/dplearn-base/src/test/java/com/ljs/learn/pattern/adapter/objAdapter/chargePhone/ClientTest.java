package com.ljs.learn.pattern.adapter.objAdapter.chargePhone;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        Phone phone = new Phone();
        // 将Voltage220V注入到VoltageAdapter中
        // 将VoltageAdapter注入到Phone中
        phone.charging(new VoltageAdapter(new Voltage220V()));
    }
}
