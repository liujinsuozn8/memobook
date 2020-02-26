package com.ljs.learn.net.udp;

import org.junit.Test;

import java.io.IOException;
import java.net.*;

public class UDPTest {
    //发送端
    @Test
    public void sender() throws IOException {
        // 创建数据报
        String str = "send message by udp";
        byte[] data = str.getBytes();
        InetAddress inet = InetAddress.getByName("127.0.0.1");
        //创建发送给用的数据报，同时绑定接收端的ip和端口
        DatagramPacket dp = new DatagramPacket(data, 0, data.length, inet, 8888);

        // 创建socket，并发送数据报
        DatagramSocket socket = new DatagramSocket();
        socket.send(dp);

        socket.close();
    }

    //接收端
    @Test
    public void recevier() throws IOException {
        // 绑定socket的端口，来接收数据报
        DatagramSocket socket = new DatagramSocket(8888);

        byte[] buffer = new byte[20];
        //创建读取用的数据报，一次性读取
        DatagramPacket dp = new DatagramPacket(buffer, 0, buffer.length);
        socket.receive(dp);

        System.out.println(new String(dp.getData(), 0, dp.getLength()));
    }
}
