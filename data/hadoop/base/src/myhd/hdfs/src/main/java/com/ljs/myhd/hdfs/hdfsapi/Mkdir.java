package com.ljs.myhd.hdfs.hdfsapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// 创建目录测试
public class Mkdir {

    // 创建目录
    @Test
    public void testMksdir01() throws IOException {
        // 手动设置用户名
        System.setProperty("HADOOP_USER_NAME", "hduser");

        // 0. 设置配置信息
        Configuration conf = new Configuration();
        // 设置 hdfs 上 NameNode 的 的访问地址
        // 参考 core-site.xml 的： fs.defaultFS
        conf.set("fs.defaultFS", "hdfs://hd02:9000");
        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(conf);

        // 2. 在hdfs上创建路径
        fs.mkdirs(new Path("/user/idea/testdir"));

        // 3. 关闭资源
        fs.close();

        System.out.println("over");
    }

    // 创建目录
    @Test
    public void testMksdir02() throws IOException, URISyntaxException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();
        // 1. 直接在获取客户端对象时，指定要访问的地址和用户名
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"),conf, "hduser");

        // 2. 在hdfs上创建路径
        fs.mkdirs(new Path("/user/idea/testdir02"));

        // 3. 关闭资源
        fs.close();

        System.out.println("over");
    }
}
