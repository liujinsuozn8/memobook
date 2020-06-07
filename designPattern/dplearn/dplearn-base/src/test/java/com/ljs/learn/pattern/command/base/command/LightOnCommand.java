package com.ljs.learn.pattern.command.base.command;

import com.ljs.learn.pattern.command.base.LightReceiver;

// 开按钮命令
public class LightOnCommand implements Command {
    LightReceiver receiver;

    public LightOnCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        // 由命令调用接收者的方法
        receiver.on();
    }

    @Override
    public void undo() {
        // 由命令调用接收者的方法
        receiver.off();
    }
}
