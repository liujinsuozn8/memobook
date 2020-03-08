package com.ljs.learn.pattern.adapter.classAdapter.chargePhone;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        Phone phone = new Phone();
        // 直接提供适配器对象，将220V电压转化为5V
        phone.charging(new VoltageAdapter());
    }
}
