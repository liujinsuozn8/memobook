package com.ljs.learn.client;

import com.ljs.learn.protocol.RequestProtocol;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * rpc客户端协议
 */
public class RPCClient {

    /**
     * 获取接口的动态代理对象，通过代理对象来执行方法
     * @param apiInterface 接口
     * @param ip 服务端ip
     * @param port 服务端端口
     * @param <T>
     * @return 接口方法的执行结果
     */
    public static <T> T getProxy(Class<T> apiInterface, String ip, int port) {
        return (T)Proxy.newProxyInstance(
                apiInterface.getClassLoader(),
                new Class<?>[]{apiInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 与服务端建立连接
                        Socket socket = new Socket(ip, port);

                        // 创建rpc协议，并设置方法调用信息
                        RequestProtocol protocol = new RequestProtocol(
                                // 接口名
                                apiInterface.getName(),
                                // 方法名
                                method.getName(),
                                // 参数类型列表
                                method.getParameterTypes(),
                                // 参数值列表
                                args
                        );

                        // 从socket中获取输出流并转化成对象流，然后向服务端发送rpc请求协议
                        ObjectOutputStream socketObjOs = new ObjectOutputStream(socket.getOutputStream());
                        socketObjOs.writeObject(protocol);

                        // 从socket中获取输入流并转化成对象流，让后获取服务端的返回结果
                        ObjectInputStream socketObjIs = new ObjectInputStream(socket.getInputStream());
                        Object result = socketObjIs.readObject();

                        return result;
                    }
                }
        );
    }
}
