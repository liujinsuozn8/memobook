package com.ljs.learn.net.tcp;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//向客户端发送文件
public class TcpTest02 {
    @Test
    public void test(){
        File file = new File("client.txt");
        System.out.println(file.getAbsoluteFile());
    }
    @Test
    public void client(){
        InetAddress inet = null;
        Socket socket = null;
        OutputStream os = null;
        FileInputStream is = null;
        try {
            // 创建socket，指明ip和端口
            inet = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inet, 8888);

            // 获取输出流
            os = socket.getOutputStream();

            // 获取文件输入流
            is = new FileInputStream(new File("client.txt"));
            // is = TcpTest02.class.getClassLoader().getResourceAsStream("client.txt");

            //读取文件，并想服务端发送数据
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
        Socket accept = null;
        InputStream is = null;
        FileOutputStream os = null;
        try {
            // 绑定端口
            serverSocket = new ServerSocket(8888);
            // 获取socket对象
            accept = serverSocket.accept();
            // 获取输入流
            is = accept.getInputStream();

            // 创建文件输出流
            os = new FileOutputStream("server.txt");

            // 从socket中读取数据并写入文件
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
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

            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
