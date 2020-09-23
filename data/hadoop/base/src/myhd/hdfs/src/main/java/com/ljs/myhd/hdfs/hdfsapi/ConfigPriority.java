package com.ljs.myhd.hdfs.hdfsapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// 通过 `fs.replication` 文件的副本数 来测试：配置设置的优先级
public class ConfigPriority {
    // 1. 使用xml文件覆盖默认配置： hdfs\src\main\resources\hdfs-site.xml
    @Test
    public void xmlPriority() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置
        Configuration conf = new Configuration();

        // 1. 获取文件系统对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 上传文件
        fs.copyFromLocalFile(
                new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/ConfigPriority/configPriority_file01.txt"),
                new Path("/user/idea/testdir")
        );

        // 3. 关闭资源
        fs.close();
    }

    // 2. 使用 `手动设置参数` 覆盖 xml文件、默认配置
    @Test
    public void setPropPriority() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置
        Configuration conf = new Configuration();
        // 手动设置副本数，来覆盖 xml文件、默认配置
        conf.set("dfs.replication", "2");

        // 1. 获取文件系统对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 上传文件
        fs.copyFromLocalFile(
                new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/ConfigPriority/configPriority_file02.txt"),
                new Path("/user/idea/testdir")
        );

        // 3. 关闭资源
        fs.close();
    }

}
