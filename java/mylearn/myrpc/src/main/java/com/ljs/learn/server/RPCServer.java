package com.ljs.learn.server;

import com.ljs.learn.protocol.RequestProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * rpc服务端框架，负责注册接口及其实现类、服务器的启动、请求的解析与处理
 */
public class RPCServer {
    // 使用Map保存接口及其实现类的映射
    private Map<String, Object> apiImplMapper = new ConcurrentHashMap<>();

    // 创建一个线程池来处理客户端请求
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    /**
     * 注册接口及其实现类
     * @param apiInterface 需要注册的接口Class对象
     * @param impl 接口的实现类
      */
    public void registImpl(Class<?> apiInterface, Object impl){
        apiImplMapper.put(apiInterface.getName(), impl);
    }

    /**
     * 启动服务
     * @param port 需要绑定的服务端口
     */
    // 启动服务
    public void startServer(int port){
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            // 循环处理网络上的请求
            while (true){
                pool.execute(new ServerTask(serverSocket.accept()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有类，负责接口请求socket并在多线程中解析并处理请求
     */
    private class ServerTask implements Runnable{
        //保存请求
        private Socket accept;

        public ServerTask(Socket accept) {
            this.accept = accept;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream objIs = new ObjectInputStream(accept.getInputStream());
                RequestProtocol protocol = (RequestProtocol) objIs.readObject();

                // 解析协议
                // 获取接口实现类
                Object instance = apiImplMapper.get(protocol.getInterfaceName());

                // 获取对象输出流
                ObjectOutputStream objOs = new ObjectOutputStream(accept.getOutputStream());

                // 如果没有获取到，则终止处理
                if (instance == null) {
                    objOs.writeObject(null);
                    // return; //直接return会客户端无法停止
                } else {
                    // 获取接口方法
                    Method method = instance.getClass().getMethod(protocol.getMethodName(), protocol.getParameterTypes());
                    // 调用方法
                    Object result = method.invoke(instance, protocol.getParameterValues());

                    // 将调用结果返回该给客户端
                    objOs.writeObject(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
