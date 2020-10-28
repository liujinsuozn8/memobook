package com.ljs.learn.zkapi.nodes;

import org.apache.zookeeper.*;

import java.io.IOException;

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
        return new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
