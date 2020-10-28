package com.ljs.learn.zkapi.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZookeeper {
    // 连接字符串
    private String connectString = "127.0.0.1:2181";
    // 会话超时时间
    private int sessionTimeout = 2000;
    // 监听器对象
    // Watcher watcher
    private ZooKeeper zkCli;

    // 连接zk
    @Before
    public void init() throws IOException {
        // String connectString, int sessionTimeout,
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
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
    }

    // 创建结点
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String path = zkCli.create("/user1", "user1data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(path);
    }

    // 获取子节点，并监控结点的变化
    @Test
    public void getDataAndWith() throws KeeperException, InterruptedException {
        List<String> children = zkCli.getChildren("/", false);
        children.forEach((String s) -> System.out.println(s));
        Thread.sleep(Integer.MAX_VALUE);
    }

    // 判断结点是否存在
    @Test
    public void exit() throws KeeperException, InterruptedException {
        Stat stat = zkCli.exists("/user1", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }
}
