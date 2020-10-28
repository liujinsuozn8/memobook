package com.ljs.learn.zkapi.nodes;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        // 2. 注册结点
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
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
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
