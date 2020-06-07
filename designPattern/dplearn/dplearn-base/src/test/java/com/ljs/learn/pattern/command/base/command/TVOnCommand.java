package com.ljs.learn.pattern.command.base.command;

import com.ljs.learn.pattern.command.base.TVReceiver;

public class TVOnCommand implements Command {
    TVReceiver receiver;

    public TVOnCommand(TVReceiver receiver) {
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
