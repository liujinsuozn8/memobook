package com.ljs.learn.net.tcp;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

// 从客户端发送文件给服务端，服务端保存到本地，并返回"发送成功"给客户端
public class TcpTest03 {
    @Test
    public void client(){
        InetAddress inet = null;
        OutputStream os = null;
        InputStream is = null;
        InputStream sockerIs = null;
        ByteArrayOutputStream baos = null;
        try {
            inet = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(inet, 8888);

            // 读取文件并输出到服务端
            os = socket.getOutputStream();
            is = new FileInputStream("client.txt");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
            }

            // 通知服务端，文件传输完成
            socket.shutdownOutput();

            // 从socket获取输入流，来获取服务端的返回信息
            sockerIs = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buffer2 = new byte[20];
            int len2 = 0;
            while((len2 = sockerIs.read(buffer2)) != -1){
                baos.write(buffer2, 0, len2);
            }

            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

            if (sockerIs != null){
                try {
                    sockerIs.close();
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

    @Test
    public void server(){
        ServerSocket serverSocket = null;
        Socket accept = null;
        InputStream is = null;
        FileOutputStream fos = null;
        OutputStream os = null;
        try {
            serverSocket = new ServerSocket(8888);
            accept = serverSocket.accept();

            // 从输入流中读取内容，并输出到文件中
            is = accept.getInputStream();
            fos = new FileOutputStream("server.txt");
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }

            // 将信息返回给客户端
            os = accept.getOutputStream();
            os.write("发送成功".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
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

            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null){
                try {
                    fos.close();
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
        }
    }
}
