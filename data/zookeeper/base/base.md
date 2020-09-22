<span id="catalog"></span>

### 目录
- [基础](#基础)
    - [概述](#概述)
    - [特点](#特点)
    - [数据结构](#数据结构)
    - [应用场景](#应用场景)
- [安装](#安装)
    - [本地模式](#本地模式)
    - [分布式安装](#分布式安装)
    - [配置参数说明](#配置参数说明)
    - [使用docker构建](#使用docker构建)
- [client端的指令](#client端的指令)
- [内部原理](#内部原理)
    - [选举机制](#选举机制)
    - [结点类型](#结点类型)
    - [client向server写数据的流程](#client向server写数据的流程)
    - [监听器原理](#监听器原理)
- [API应用](#API应用)
    - [maven配置](#maven配置)
    - [创建zookeeper的client对象](#创建zookeeper的client对象)
    - [client对象的操作](#client对象的操作)
    - [Stat结构体的属性](#Stat结构体的属性)
    - [监听服务器节点动态上下线](#监听服务器节点动态上下线)
- [](#)

# 基础
## 概述
[top](#catalog)
- Zookeeper是一个开源的分布式的，为分布式应用提**供协调服务**的软件

- 一个基于 `观察者模式` 的分布式服务管理框架
    - 负责存储和管理大家都关心的数据，然后接收观察者注册
    - 数据的状态发送变化时，zookeeper将负责通知已经在zookeeper上注册的观察者
        - 观察者接受到通知之后，在做出响应

- **zookeeper = 文件系统 + 通知机制**

## 特点
[top](#catalog)
- 集群的组成
    - 一个 `leader` + 多个 `follower`
- 集群中只要有半数以上结点存活，zookeeper集群就能正常服务
    - 可用结点数<span style='color:red'>必须 大于 1/2</span>，不能等于
    - 如4台机器坏了2个，剩余2个，则无法工作
- 全局数据一致
    - 每个 server 都保存**相同的**数据副本
    - client 无论连接哪个 server，数据都一致
- 更新请求顺序执行
    - 来自同一个 client 的更新请求按其发送顺序依次执行
- 事务特性
    - 原子性: 一次更新只能成功，或只能失败
- 实时性
    - 在一定时间范围内，client 能读到最新数据
    - 即: **同步副本的时间非常短，数据量小**

## 数据结构
[top](#catalog)
- 类似于linux文件系统，即树形结构
- 每一个结点称为 `ZNode`
- 每一个 `ZNode` 默认能够存储 1MB 的数据
- 每个 `ZNode` 都可以通过**路径**唯一标识
- 数据结构示例
    - ![图](?????)

## 应用场景
[top](#catalog)
- 统一命名服务
    分布式环境下，经常需要读应用/服务进行统一命名，便于识别
    - ????? ppt
- 统一配置管理
    - 配置同步
        - 一般要求一个集群中所有节点的配置信息相同
        - 修改配置文件之后，需要快速同步到各个节点
    - 配置信息可有Zookeeper进行管理
        - 将配置信息写入Zookeeper上的一个 Znode
        - 每个客户端服务器监听这个Znode
        - 当 Znode 的数据被修改时，Zookeeper 将通知各个 client
    - ????? ppt
- 统一集群状态管理
    - 分布式环境中，需要实时掌握每个节点的状态，并根据状态做出调整
    - zookeeper 监控节点状态
        - 将节点信息写入 Zookeeper 上的一个 Znode
        - 监听这个 Znode 可以获取目标节点的实时状态
    - ????? ppt
- 服务器节点动态上下线
    - 工作流程
        - ![图](?????)

    - 流程说明
        1. server 启动时，去注册信息
            - 一般都是临时节点
        2. client 获取到当前在线的服务器列表，并注册监听
        3. 如果服务器节点下线
        4. 将: `服务器节点上下线事件` 发送给各个客户端（观察者）
        5. 通过 `Zookeeper` 对象的 `process()` 方法获取通知，并重新获取服务器列表，并监听

- 软负载均衡等
    - 在 zookeeper 中记录每台服务器的访问数，让访问量最少的服务器处理最新的请求
    - ????? ppt

# 安装
## 本地模式
[top](#catalog)
- 配置zookeeper
    1. 安装 jdk
    2. 将 zookeeper 解压到指定目录下
    3. 修改配置
        1. 拷贝配置文件
            ```sh
            cp conf/zoo_sample.cfg zoo.cfg
            ```
        2. 修改 `dataDir` 路径
            ```
            dataDir=/zkdata
            ```
        3. 创建 `dataDir` 指定的目录
    4. 添加 `zookeeper` 的环境变量
        ```sh
        export ZOOKEEPER_HOME='....'
        export PATH=$PATH:$ZOOKEEPER_HOME/bin
        ```
- 启动 server
    1. 启动 zookeeper 的 server 端
        - 直接启动 `zkServer.sh start`
        - 或者在 zookeeper 的解压目录下使用 `bin/zkServer.sh start` 启动
    2. 检查进程: `jps`
        ```sh
        50 Jps
        26 QuorumPeerMain
        ```
    3. 查看 server 的状态: `zkServer.sh status`
        ```
        ZooKeeper JMX enabled by default
        Using config: /opt/module/zookeeper-3.4.14/bin/../conf/zoo.cfg
        Mode: standalone <<<< 单机模式
        ```
- 停止server: `zkServer.sh stop`
- 启动客户端
    1. 启动: `zkCli.sh`
    2. 检查 Znode: `ls /`
    3. 退出: `quit`

## 分布式安装
[top](#catalog)
1. 各个机器上配置 zookeeper
    1. 参考: [本地模式](#本地模式) 的 配置zookeeper
    2. 在 zkData 目录下创建一个 `myid` 文件
    3. 在 `myid` 文件中添加当前机器的`数字编号`
        - 从 1 开始
    4. 在 `conf/zoo.cfg` 中添加其他 zookeeper 机器的信息
        - 示例
            ```
            server.数字编号=<主机名/ip>:与Leader通信的端口:选举Leader时的通信端口
            server.1=zk01:2888:3888
            ```
        - 可以写主机名，也可以写 ip，但是需要配置 ssh 免密登录
        - 与Leader通信的端口
            - 用于服务器与集群中的 Leader 服务器交换数据
        - 选举Leader时的通信端口
            - 如果集群中的 Leader 服务器坏了，需要一个端口用于重新选举
            - 该端口只用于重新选举 Leader 时，各服务器间的相互通信

2. 在各个机器之间创建 ssh免密连接
3. 启动**所有** server
    - 启动 server: `zkServer.sh start`
    - 检查zookeeper状态: `zkServer.sh status`

## 配置参数说明
[top](#catalog)

|参数|功能|
|-|-|
|tickTime=2000|心跳 2s|
|initLimit=10|leader 与 follower 之间初始通信的最大心跳帧|
|syncLimit=5|启动之后，leader 与 follower 之间初始通信的最大心跳帧|
|dataDir=/zkdata|存储数据的目录|
|clientPort=2181|客户端访问的端口号|

## 使用docker构建
[top](#catalog)
- 构建本地环境
    ```sh
    docker run -it --name zk01 -v <...>/zoo.cfg:/zoo.cfg  zklocal
    ```

- 构建集群
    - 创建网桥
        ```sh
        docker network create --driver bridge --subnet 172.21.0.0/16 zkbr
        ```
    - 在网桥下创建3个容器
        ```
        docker run -it --name=zk01 -p=2181:2181 -v=<...>/zoo.cfg:/zoo.cfg  -h=zk01 --network=zkbr --ip=172.21.100.101 --add-host=zk02:172.21.100.102 --add-host=zk03:172.21.100.103 zklocal

        docker run -it --name=zk02 -v=<...>/zoo.cfg:/zoo.cfg  -h=zk02 --network=zkbr --ip=172.21.100.102 --add-host=zk01:172.21.100.101 --add-host=zk03:172.21.100.103 zklocal

        docker run -it --name=zk03 -v=<...>/zoo.cfg:/zoo.cfg  -h=zk03 --network=zkbr --ip=172.21.100.103 --add-host=zk01:172.21.100.101 --add-host=zk02:172.21.100.102 zklocal 
        ```
    - 在三台容器之间创建 ssh 免密连接
    - 写入 myid
        ```sh
        echo 1 > /zkdata/myid
        echo 2 > /zkdata/myid
        echo 3 > /zkdata/myid
        ```

# client端的指令
[top](#catalog)
- 创建节点时，**必须要有数据**
- 指令
    |指令|功能|
    |-|-|
    |`create /结点路径 "数据"`|创建持久结点|
    |`create -e /结点路径 "数据"`|创建临时结点|
    |`create -s /结点路径 "数据"`|创建带序号的结点|
    |`get /结点路径`|从结点中获取数据|
    |`set /结点路径 "新的数据"`|修改结点数据|
    |`get /结点路径 watch`|监听结点**数据**的变化<br>只能监听一次<br>监听成功后，如果再次监听，需要重新执行指令|
    |`ls /结点路径 watch`|监听结点下路径的变化<br>只能监听一次<br>监听成功后，如果再次监听，需要重新执行指令|
    |`delete /结点路径`|删除结点|
    |`rmr /结点路径`|递归删除结点|
    |`stat /结点路径`|查看结点状态|

# 内部原理
## 选举机制
[top](#catalog)
- 半数机制
    - 集群中半数以上机器存活，集群可用
    - Zookeeper适合安装**奇数台**服务器
- 自动选举 Leader
    - 在配置文件中不需要指定 Leader、Follower
    - Leader是通过内部的选举机制临时产生的
    - Zookeeper工作时，一定有一个节点为Leader，其他为Follower
- 选举过程
    - 示例: 5台机器选举 Leader
        - 机器id: 1、2、3、4、5
        - 按照 id 的顺序启动 zookeeper
    - 一个原则
        - 每个机器先选自己，然后再选编号最大的
    - 整体流程
        1. `server.1` 启动
            - 此时只有一台服务器启动
            - 发出的报文没有响应，所以 `server.1.选举状态=LOOKING`
        2. `server.2` 启动
            - 与 `server.1` 通信，互相交换自己的选举结果
            - 因为两个 server 都没有历史数据，所以<span style='color:red'>id 较大的 server.2 胜出</span>
            - 但是没有达到**超过半数**的服务器选举 `server.2`，所以:
                - `server.1.选举状态=LOOKING`
                - `server.2.选举状态=LOOKING`
        3. `server.3` 启动
            - 与 `server.1`、`server.2` 通信，互相交换自己的选举结果
            - id 较大的 `server.3` 胜出，并且**超过了半数** 服务器，成为了 Leader
        4. `server.4` 启动
            - 已经有 Leader 了，自动变成 Follower
        5. `server.5` 启动
            - 已经有 Leader 了，自动变成 Follower

## 结点类型
[top](#catalog)
- 两种类型的节点
    - 持久化节点，客户端和服务器断开连接后，创建的结点不删除
    - 临时节点，客户端和服务器断开连接后，创建的结点自动删除

- 节点细分

    ||普通节点<br>（client 与 server 断开连接后）|顺编号目录节点<br>（client 与 server 断开连接后）|
    |-|-|-|
    |持久化|结点依旧存在|结点依旧存在<br>创建时只需要指定节点名，ZK会自动为结点添加顺序编号|
    |临时|结点被删除|结点被删除<br>创建时只需要指定节点名，ZK会自动为结点添加顺序编号|

- 顺序编号节点的序号
    - 顺序号是一个单调递增的计数器，由父节点维护

    - 在分布式系统中，顺序号可以被用于为所有的事件进行全局排序，这样 client 可以通过顺序号推断**机器上线的先后顺序**

## client向server写数据的流程
[top](#catalog)
- 写数据的流程图
    - ![图](?????)
- 流程说明
    1. `client ---> server1`
        - client 向 server1 写数据，发送一个写请求
    2. server1
        - 检查 server1 是不是 Leader
            - 如果 server1 不是 Leader，则 Server1 会把请求转发给 Leader
    3. `Leader ---> FollowerX`
        - 写请求到达 Leader
        - Leader 再将写请求广播给所有 Follower
    4. `FollowerX ---> Leader`
        - 每个 Follower 接受到 Leader 的写请求，执行写操作
        - 操作完成后，通知 Leader
    5. `1/2 以上的 FollowerX ---> Leader`
        -  当 leader 接收到大多数server数据写成功了，说明**数据写成功了**
        - 如果有3个结点，只要有两个写成功了，就认为数据写成功了
    6. `Leader ---> server1`
        - Leader 通知 server1 数据写入成功
    7. `server1 ---> client`
        - server1 通知 client 数据写成功
    8. 此时认为整个写操作成功

## 监听器原理
[top](#catalog)
- 监听的流程图
    - ![图](?????)

- 流程说明
    1. 先有一个 main() 线程
    2. 在main 线程中创建 zookeeper 的 client 对象，这时就会创建两个线程
        - 一个负责网络通信 `connect`
        - 一个负责监听 `listener`
    3. 通过 `connect` 线程将注册的监听事件发送给 zookeeper
    4. 在 zookeeper 的注册监听器列表中将注册的监听事件添加到列表中
    5. zookeeper 监听到有数据或路径变化，就会将这个消息发送给 `listener` 线程
    6. `listener` 线程内部调用 `process()` 方法来处理消息

- 常见的监听
    - 监听结点数据的变化
        - `get path watch `
    - 监听结点数据的变化
        - `ls path watch`

# API应用
## maven配置
[top](#catalog)
- 参考
    - [src/zk-api/pom.xml](src/zk-api/pom.xml)
- 配置内容
    ```xml
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.8.2</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper -->
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.4.10</version>
    </dependency>
    ```

## 创建zookeeper的client对象
[top](#catalog)
- 参考
    - [src/zk-api/src/main/java/com/ljs/learn/zkapi/client/TestZookeeper.java](src/zk-api/src/main/java/com/ljs/learn/zkapi/client/TestZookeeper.java)
- 代码内容
    ```java
    // 多个地址之间通过 `,` 分割，中间不能有【空格】
    // private String connectString = "127.0.0.1:2181,127.0.0.2:2181";
    // 连接字符串
    private String connectString = "127.0.0.1:2181";
    // 会话超时时间
    private int sessionTimeout = 2000;
    // 监听器对象，即 listener
    // Watcher watcher
    private ZooKeeper zkCli;

    // 连接zk
    @Before
    public void init() throws IOException {
        // ZooKeeper(连接字符串, 会话超时时间， 监听器对象)
        // 创建 client 对象
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            // 有 listener 负责调用 process 方法
            @Override
            public void process(WatchedEvent watchedEvent) {
                //..
            }
        });
    }
    ```

## client对象的操作
[top](#catalog)
- 参考
    - [src/zk-api/src/main/java/com/ljs/learn/zkapi/client/TestZookeeper.java](src/zk-api/src/main/java/com/ljs/learn/zkapi/client/TestZookeeper.java)
- 创建节点
    ```java
    String path = zkCli.create("/user1", "user1data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    // ZooDefs.Ids.OPEN_ACL_UNSAFE  节点的访问权限
    // CreateMode.PERSISTENT 增加节点的模式: 持久化节点
    ```

- 判断节点是否存在
    ```java
    Stat stat = zkCli.exists("/user1", false);
    System.out.println(stat == null ? "not exist" : "exist");
    ```

- 获取子节点，并监控节点的变化
    - 启动监听
        ```java
        // 1. 获取 "/" 目录下的子节点，并监听
        List<String> children = zkCli.getChildren("/", false);
        // 2. 输出子节点
        children.forEach((String s) -> System.out.println(s));
        // 3. 使程序一直睡眠，监听 server 端的变化
        Thread.sleep(Integer.MAX_VALUE);
        ```
    - 监听结果处理
        ```java
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            // 在client对象中注册监听函数
            @Override
            public void process(WatchedEvent watchedEvent) {
                List<String> children = null;
                try {
                    children = zkCli.getChildren("/", true);
                    children.forEach((String s) -> System.out.println(s));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ```

## Stat结构体的属性
[top](#catalog)
- 属性

    |属性           |含义|
    |--------------:|-|
    |czxid          |创建节点的事务zxid|
    |ctime          |znode被创建的毫秒数(从1970年开始)|
    |mzxid          |znode最后更新的事务zxid|
    |mtime          |znode最后修改的毫秒数(从1970年开始)|
    |pZxid          |znode最后更新的子节点zxid|
    |cversion       |znode子节点变化号，znode子节点修改次数|
    |dataversion    |znode数据变化号|
    |aclVersion     |znode访问控制列表的变化号|
    |ephemeralOwner |如果是临时节点，表示znode拥有者的`session id`<br>如果不是临时节点则是`0`|
    |dataLength     |znode的数据长度|
    |numChildren    |znode子节点数量|

- ZooKeeper事务ID: `zxid`
    - 每次修改ZooKeeper状态都会收到一个zxid形式的时间戳，即: ZooKeeper事务ID
    - 每个修改都有唯一的zxid
    - 事务ID表示的是 ZooKeeper 中所有修改的次序
        - 如: `zxid1 < zxid2`，则 `zxid1` 先发生，`zxid2` 后发生

## 监听服务器节点动态上下线
[top](#catalog)
- 测试方法
    1. 启动 zookeeper 集群
    2. 创建 `/servers` 目录
    3. 启动客户端节点，准备监听 `/servers` 结点
        - 监听之前，`/servers` 必须存在，否则会抛出异常
    4. 启动多个服务器端节点，观察客户端节点的数据
        - 每个服务器节点启动后都会创建一个临时节点
    5. 依次关闭服务器端节点，观察客户端节点的数据
        - 每次关闭一个服务器节点后，zookeeper上的临时节点会被删除
- 服务器节点
    - 参考
        - [src/zk-api/src/main/java/com/ljs/learn/zkapi/nodes/DistributeServer.java](src/zk-api/src/main/java/com/ljs/learn/zkapi/nodes/DistributeServer.java)
    - 代码内容
        ```java
        public class DistributeServer {
            // 连接字符串
            private String connectString = "127.0.0.1:2181";
            // 会话超时时间
            private int sessionTimeout = 2000;

            private ZooKeeper zkCli;

            public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
                DistributeServer server = new DistributeServer();

                // 1. 连接 zookeeper 集群
                ZooKeeper connect = server.getConnect();

                // 2. 注册结点
                server.register(args[0]);

                // 3. 业务逻辑
                server.business();
            }

            private void business() throws InterruptedException {
                Thread.sleep(Long.MAX_VALUE);   // 延迟存活时间
            }

            // 创建 结点
            private void register(String hostname) throws KeeperException, InterruptedException {
                String path = zkCli.create(
                        "servers/server",
                        hostname.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT_SEQUENTIAL // 生成临时的、带序号的结点，这样可以自动生成不同的结点
                );

                System.out.println(path + "is working");
            }

            private ZooKeeper getConnect() throws IOException {
                // 创建 zookeeper 的client 对象
                // 因为是服务端操作，所以不做任何监听处理
                return new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {

                    }
                });
            }
        }
        ```

- 客户端
    - 参考
        - [src/zk-api/src/main/java/com/ljs/learn/zkapi/nodes/DistributeClient.java](src/zk-api/src/main/java/com/ljs/learn/zkapi/nodes/DistributeClient.java)
    - 代码内容
        ```java
        public class DistributeClient {
            // 连接字符串
            private String connectString = "127.0.0.1:2181";
            // 会话超时时间
            private int sessionTimeout = 2000;

            private ZooKeeper zkCli;

            public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
                DistributeClient server = new DistributeClient();

                // 1. 连接 zookeeper 集群
                ZooKeeper connect = server.getConnect();

                // 2. 获取节点数据，并监听
                server.getChildren();

                // 3. 业务逻辑
                server.business();
            }

            private void getChildren() throws KeeperException, InterruptedException {
                List<String> children = zkCli.getChildren("/servers", true);
                List<String> hosts = new ArrayList<>();
                for (String child : children) {
                    // 不需要监听，children已经监听了父路径
                    byte[] data = zkCli.getData("/servers/" + child, false, null);
                    hosts.add(new String(data));
                }

                // 输出所有在线主机
            }

            private void business() throws InterruptedException {
                Thread.sleep(Long.MAX_VALUE);   // 延迟存活时间
            }

            private ZooKeeper getConnect() throws IOException {
                return new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                    // 4. 监听响应
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        try {
                            // 5. 每次获取数据后，重新监听
                            getChildren();
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        ```

[top](#catalog)