package com.ljs.learn.pattern.mediator.imporve.colleague;

import com.ljs.learn.pattern.mediator.imporve.mediator.Mediator;

// 具体同事类
public class Alarm extends Colleague {
    public Alarm(Mediator mediator, String name) {
        super(mediator, name);

        // 创建对象的同事，将自己注册到 中介者中
        mediator.register(name, this);
    }

    public void sendAlarm(int stateChange){
        sendMessage(stateChange);
    }

    @Override
    public void sendMessage(int stateChange) {
        // 调用中介者对象
        this.getMediator().getMessage(stateChange, this.name);
    }
}
