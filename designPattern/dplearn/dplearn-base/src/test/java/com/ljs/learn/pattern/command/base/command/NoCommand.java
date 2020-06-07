package com.ljs.learn.pattern.command.base.command;

/**
    没有任何命令，即空执行。可用于初始化每个Command对象
    当调用空命令时，对象什么都不做
    也可以作为一种设计模式，可以省掉对空的判断
* */

public class NoCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
