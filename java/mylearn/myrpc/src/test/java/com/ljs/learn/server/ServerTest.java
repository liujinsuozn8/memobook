package com.ljs.learn.server;

import com.ljs.learn.rpcapi.UserInterface;
import com.ljs.learn.rpcapiimpl.UserImpl;
import org.junit.Test;

public class ServerTest {
    @Test
    public void server(){
        RPCServer rpcServer = new RPCServer();

        // 注册接口及其实现类，只注册UserInterface一个测试接口
        rpcServer.registImpl(UserInterface.class, new UserImpl());

        // 启动服务
        rpcServer.startServer(8888);
    }
}
