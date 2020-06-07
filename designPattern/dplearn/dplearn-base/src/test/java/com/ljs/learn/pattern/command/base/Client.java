package com.ljs.learn.pattern.command.base;

import com.ljs.learn.pattern.command.base.command.LightOffCommand;
import com.ljs.learn.pattern.command.base.command.LightOnCommand;
import com.ljs.learn.pattern.command.base.command.TVOffCommand;
import com.ljs.learn.pattern.command.base.command.TVOnCommand;
import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 1. 创建一个app，即创建一个请求发送者
        RemoteController app = new RemoteController();

        // 2. 创建命令
        // 2.1 创建与light相关的命令
        // 创建请求接受者
        LightReceiver lightReceiver = new LightReceiver();

        // 创建命令
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);

        // 2.2 创建与TV相关的命令
        TVReceiver tvReceiver = new TVReceiver();
        TVOnCommand tvOnCommand = new TVOnCommand(tvReceiver);
        TVOffCommand tvOffCommand = new TVOffCommand(tvReceiver);

        // 3. 为app设置命令
        app.setCommand(0, lightOnCommand, lightOffCommand);
        app.setCommand(1, tvOnCommand, tvOffCommand);

        // 4. 通过app的安装来执行操作
        // light 操作
        app.onButtonWasPushed(0);
        app.offButtonWasPushed(0);
        app.undo();

        // tv 操作
        app.onButtonWasPushed(1);
        app.offButtonWasPushed(1);
        app.undo();
    }
}
