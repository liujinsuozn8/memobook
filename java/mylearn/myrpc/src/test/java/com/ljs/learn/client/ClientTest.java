package com.ljs.learn.client;

import com.ljs.learn.rpcapi.OtherInterface;
import com.ljs.learn.rpcapi.UserInterface;
import org.junit.Test;

public class ClientTest  {
    @Test
    public void client(){
        // 测试已注册的接口
        UserInterface proxy01 = RPCClient.getProxy(UserInterface.class, "127.0.0.1", 8888);
        String result01 = proxy01.createUser("testUser");
        System.out.println(result01);

        System.out.println("--------——");
        // 测试未注册的接口
        OtherInterface proxy02 = RPCClient.getProxy(OtherInterface.class, "127.0.0.1", 8888);
        String result02 = proxy02.otherTest("test");
        System.out.println(result02);
    }
}
