package com.ljs.learn.pattern.command.base.command;

// 命令接口
public interface Command {
    // 执行某个操作
    void execute();
    // 撤销操作
    void undo();
}
