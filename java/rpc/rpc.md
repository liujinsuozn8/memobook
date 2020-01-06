<span id="catalog"></span>

- 参考：https://www.bilibili.com/video/av73288542

- [基本概念](#基本概念)
- [调用如何实现](#调用如何实现)
    - [通过代理设计模式调用](#通过代理设计模式调用)
- [](#)


# 基本概念
[top](#catalog)
- RPC的本质是：**远程过程调用**
    - 由服务消费者**通过网络**远程机器上的接口实现，称为一个过程

- 服务提供者和服务消费者的概念
    - 服务提供者
        - 服务提供者就是**完成服务消费者具体功能的实现**
        - 提供对应的接口与实现
    - 服务消费者
        - 使用**服务提供者**提供的接口方法
        - 使用**服务提供者**方法
            - 导入：服务提供者暴露的接口
            - 调用，**提供调用方法对象参数，不需要知道具体逻辑的实现**
    - 示例
        - 服务器提供者
            ```java
            package server.supplier;
            // 接口
            interface IUserService {
                Object getUser();
            }
            
            // 实现
            class UserServiceImpl implements IUserService{
                Object getUser() {

                }
            }
            ```
        - 服务器消费者
            ```java
            package server.consume;
            import server.supplier;

            class Consume{
                public static void main(){
                    // 需要解决如果调用的问题
                    IUserService ius = new ??????; 
                    ius.getUser();
                    System.out.print(ius);
                }
            }
            ```

- 实际的部署与调用状况
    ```
    A--server           B--server
    服务提供者             服务消费者
    |                       |
    |----------网络----------|   
    ```

- **需要解决的问题**
    - 服务消费者只有接口，**如何通过接口**来**调用远程**服务中的**具体逻辑**

- RPC涉及到的内容
    - 反射
    - 序列化
    - 动态代理
    - 多线程
    - 线程池

# 调用如何实现
## 通过代理设计模式调用
[top](#catalog)
- **在消费端**，通过动态代理，动态生成这个接口对应实现类的实例
- 示例
    - 服务器提供者
        ```java
        package server.supplier;
        // 接口
        interface IUserService {
            Object getUser();
        }
        
        // 实现
        class UserServiceImpl implements IUserService{
            Object getUser() {

            }
        }
        ```
    - 服务器消费者
        ```java
        package server.consume;
        import server.supplier.*;

        class UserServiceProxy{
            
        }

        class Consume{
            public static void main(){
                IUserService ius = new ;
                ius.getUser();
                System.out.print(ius);
            }
        }
        ```

- 代码
- 
```java
// RPC 客户端
public class RPCClient {

    /**
     * 通过动态代理，获取调用接口对应的实例
     * @param interfaceClass
     * @param address
     * @param <T>
     * @return
     */
    public static <T> T getRemoteProxy(Class<T> interfaceClass, InetSocketAddress address){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),//通过 interfaceClass 类加载器
                new Class<?>[]{interfaceClass},//代理的接口，需要一个数组
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 连接服务端
                        try(Socket socket = new Socket()) {
                            socket.connect(address);

                            try(
                                //获取输出流，并转化为对象流
                                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                                //获取输入流，并转化为对象流
                                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                            ){
                                //创建一个RPC框架中请求协议对象
                                RequestProtocol requestProtocol = new RequestProtocol();
                                // 填充属性
                                requestProtocol.setInterfaceClassName(interfaceClass.getName());
                                requestProtocol.setMethodName(method.getName());
                                requestProtocol.setParamTypes(method.getParameterTypes());
                                requestProtocol.setParamValues(args);

                                // 序列化协议对象 == 将对象数据放入网络中
                                os.writeObject(requestProtocol);

                                // 反序列化 == 获取服务端返回的数据
                                Object result = is.readObject();

                                return result;
                            }catch (Exception e) {

                            }

                        } catch (Exception e){

                        }
                        return null;
                    }
                }//接口的实例
        );
    }
}

```


```java
// RPC框架请求协议类，能够在网络中进行传递
public class RequestProtocol implements Serializable {
    // 接口名称
    private String interfaceClassName;
    // 方法名称
    private String methodName;
    // 参数类型列表
    private Class<?>[] paramTypes;
    // 参数值列表
    private Object[] paramValues;

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public void setInterfaceClassName(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }
}

```
```java
/**
 * rpc 框架 服务端实现
 * 核心步骤
 * 1. 暴露可以调用的服务端接口
 * 2. 启动服务器
 */

public class RPCServer {
    // 定义存储暴露服务列表
    Map<String, Object> serverMap = new ConcurrentHashMap<>(32);

    // 定义一个线程池
    ThreadPoolExecutor pool = new ThreadPoolExecutor(
            8,
            20,
            200,
            TimeUnit.MICROSECONDS,
            new ArrayBlockingQueue(10)
    );

    /**
     * 暴露服务的方法
     * @param interfaceClass 需要暴露的接口
     * @param instance 在接口有多个实现类时，指定该接口对应的是哪一个实例，通过KV对来进行保存
     */
    public void publishServiceAPI(Class<?> interfaceClass, Object instance){
        this.serverMap.put(interfaceClass.getName(), instance);
    }

    /**
     * 发布服务
     * @param port 监听端口
     */
    public void start(int port){
        try {
            // 创建网络服务端
            ServerSocket serverSocket = new ServerSocket();
            // 绑定端口
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("RPC Server Starting");

            while(true){
                pool.execute(new ServerTask(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建客户端请求处理的线程类
    private class ServerTask implements Runnable{
        //处理时，需要知道处理的是哪一个客户端请求
        private final Socket socket;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())
            ){
                // 反序列化== 获取客户端传入的协议数据
                RequestProtocol requestProtocol = (RequestProtocol) is.readObject();
                // 通过协议对象来调用接口方法
                // 1. 获取接口名称
                String interfaceClassName = requestProtocol.getInterfaceClassName();
                // 2. 从暴露的服务列表中查询该接口名称
                Object instance = serverMap.get(interfaceClassName);
                if (instance == null)
                    return;

                // 3. 创建一个方法对象
                Method method = instance.getClass().getDeclaredMethod(
                        requestProtocol.getMethodName(),
                        requestProtocol.getParamTypes()
                );

                // 4. 调用该方法，并取得结果
                Object result = method.invoke(requestProtocol.getParamValues());

                // 5. 将结果序列化
                os.writeObject(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

```
```java
// 定义暴露给客户端的服务接口
public interface UserService {
    String addUserName(String name);
}

```
```java
public class UserServiceImpl implements UserService {
    @Override
    public String addUserName(String name) {
        return "a new name : " + name;
    }
}

```