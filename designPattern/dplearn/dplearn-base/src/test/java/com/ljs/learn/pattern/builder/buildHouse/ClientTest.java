package com.ljs.learn.pattern.builder.buildHouse;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        CommonHose commonHose = new CommonHose();
        commonHose.build();
    }
}
