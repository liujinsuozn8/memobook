package com.ljs.learn.pattern.visitor.base;

// 双分派的第二次分派
// 1. 第一次分派：在客户端程序中，将具体状态作为参数传递到Woman中
// 2. 第二次分派：Woman类调用作为参数的具体方法 getWomanResult，
//              同时将自己作为参数传入
public class Man extends Person {
    @Override
    public void accept(Action action) {
        action.getManResult(this);
    }
}
