package com.ljs.learn.net.tcp;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

// 客户端发送信息给服务端，服务端将数据显示在控制台
public class TcpTest01 {
    @Test
    public void client(){
        Socket socket = null;
        OutputStream os = null;
        try {
            // 1. 创建Socket对象，指明服务端的ip和端口号
            InetAddress inet = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inet, 8888);

            // 2. 从Socket对象中获取一个输出流，用于输出数据
            os = socket.getOutputStream();

            // 3. 通过输出流向服务端发送数据
            os.write("hello, this is client".getBytes());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            // 关闭流
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //关闭连接
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void server(){
        ServerSocket serverSocket = null;
        InputStream is = null;
        Socket accept = null;
        ByteArrayOutputStream baos = null;
        try {
            // 1. 创建服务端的ServerSocket，指明服务器自身的端口号
            serverSocket = new ServerSocket(8888);
            // 2. 调用`accpet()`，接收来自于客户端的socket
            accept = serverSocket.accept();

            // 3. 从socket中获取一个输入流
            is = accept.getInputStream();

            // 4. 读取输入流中的数据

            // 可能会产生乱码，所以一般不使用这种方法
            // byte[] bs = new byte[20];
            // int len = 0;
            // String str = null;
            // while((len = is.read(bs)) != -1){
            //     str = new String (bs,0, len);
            //     System.out.println(str);
            // }

            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[5];
            int len = 0;

            // 每次读取5个字节，并存储到baos的临时区域中
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            // 将baos转换为字符串，即可防止乱码
            System.out.println(baos.toString());

            System.out.println("client Address : " + accept.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭资源
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (accept != null){
                try {
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
