package com.ljs.learn.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
    // IP地址测试
    @Test
    public void test01() {
        try {
            // 使用IP地址
            InetAddress inet = InetAddress.getByName("192.168.0.0");

            //output : /192.168.0.0
            System.out.println(inet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 域名测试
    @Test
    public void test02(){
        try {
            InetAddress inet = InetAddress.getByName("www.baidu.com");

            System.out.println(inet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 本地回环地址测试
    @Test
    public void test03(){
        try {
            InetAddress inet = InetAddress.getByName("127.0.0.1");
            System.out.println(inet);

            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);

            System.out.println("HostAddress：" + localHost.getHostAddress());
            System.out.println("HostName：" + localHost.getHostName());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
