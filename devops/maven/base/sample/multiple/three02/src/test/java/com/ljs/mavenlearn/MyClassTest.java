package com.ljs.mavenlearn;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyClassTest {
    @Test
    public void test(){
        MyClass obj = new MyClass();
        String show = obj.show();
        assertEquals (show,  "this is show");
    }
}
