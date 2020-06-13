package com.ljs.learn.pattern.mediator.imporve.mediator;

import com.ljs.learn.pattern.mediator.imporve.colleague.Colleague;

public abstract class Mediator {
    // 将中介者对象保存到集合中
    public abstract void register(String colleagueName, Colleague colleague);
    // 接收由具体同事对象发出的消息
    public abstract void getMessage(int stateChange, String colleagueName);
    //
    public abstract void sendMessage();
}
