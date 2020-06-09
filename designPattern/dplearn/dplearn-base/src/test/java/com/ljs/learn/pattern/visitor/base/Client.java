package com.ljs.learn.pattern.visitor.base;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        ObjectStructure os = new ObjectStructure();

        os.attach(new Man());
        os.attach(new Woman());
        os.attach(new Woman());
        os.attach(new Man());

        Success success = new Success();

        // 双分派的第一次分派
        // 1. 第一次分派：在客户端程序中，将具体状态作为参数传递到Woman中
        // 2. 第二次分派：Woman类调用作为参数的具体方法 getWomanResult，
        //              同时将自己作为参数传入
        os.display(success);
    }
}
