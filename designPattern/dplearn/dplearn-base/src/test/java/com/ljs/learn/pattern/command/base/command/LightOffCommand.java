package com.ljs.learn.pattern.command.base.command;

import com.ljs.learn.pattern.command.base.LightReceiver;

// 关按钮命令
public class LightOffCommand implements Command {
    LightReceiver receiver;

    public LightOffCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        // 由命令调用接收者的方法
        receiver.off();
    }

    @Override
    public void undo() {
        // 由命令调用接收者的方法
        receiver.on();
    }
}
