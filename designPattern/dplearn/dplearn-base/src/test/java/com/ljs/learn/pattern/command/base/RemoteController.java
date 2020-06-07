package com.ljs.learn.pattern.command.base;

import com.ljs.learn.pattern.command.base.command.Command;
import com.ljs.learn.pattern.command.base.command.NoCommand;

// 请求发送者：手机app
public class RemoteController {
    // 保存按钮的命令
    private Command[] onCommand;
    private Command[] offCommand;

    // 保存最近一次的命令，以便执行撤销操作
    private Command undoCommand;

    public RemoteController() {
        onCommand = new Command[5];
        offCommand = new Command[5];

        for (int i = 0; i < 5; i++) {
            onCommand[i] = new NoCommand();
            offCommand[i] = new NoCommand();
        }
    }

    // 给按钮设置需要的命令
    // 同时设置开、关的命令
    public void setCommand(int no, Command onCommand, Command offCommand){
        this.onCommand[no] = onCommand;
        this.offCommand[no] = offCommand;
    }

    // 开按钮操作
    public void onButtonWasPushed(int no){
        onCommand[no].execute();
        // 记录按钮，以便执行撤销
        undoCommand = onCommand[no];
    }

    // 关按钮操作
    public void offButtonWasPushed(int no){
        offCommand[no].execute();
        // 记录按钮，以便执行撤销
        undoCommand = offCommand[no];
    }

    // 撤销操作
    public void undo(){
        undoCommand.undo();
    }
}
