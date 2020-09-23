package com.ljs.myhd.hdfs.hdfsapi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// hdfs 文件操作测试
public class FileAction {
    // 拷贝本地文件到 hdfs
    @Test
    public void copyFromLocal() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 拷贝本地文件到服务器
        fs.copyFromLocalFile(
                new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileAction_file01.txt"),
                new Path("/user/idea/testdir")
        );

        // 3. 关闭资源
        fs.close();
    }

    // 下载文件测试，会生成 crc 校验文件
    @Test
    public void copyToLocalFile() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 下载文件，参数为： 服务器文件目录、本地文件目录
        fs.copyToLocalFile(
                new Path("/user/idea/testdir/fileAction_file01.txt"),
                new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileDownload/")
        );

        // 3. 关闭资源
        fs.close();
    }

    // 下载文件的重载方法。不使用 crc 校验文件
    @Test
    public void copyToLocalFileNotCRC() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 下载文件，参数为： 服务器文件目录、本地文件目录
        fs.copyToLocalFile(
                false, // 不删除原文件
                new Path("/user/idea/testdir/fileAction_file01.txt"),
                new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileDownload/"),
                true    // 不使用crc
        );

        // 3. 关闭资源
        fs.close();
    }

    // 删除文件
    @Test
    public void deleteFile() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 删除文件
        fs.delete(
                new Path("/user/idea/testdir/fileAction_file01.txt"),
                true    // true 可以删除目录
        );

        // 3. 关闭资源
        fs.close();
    }

    // 重命名 hdfs 上保存的文件
    @Test
    public void rename() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 删除文件
        fs.rename(
                new Path("/user/idea/testdir/fileAction_file01.txt"),
                new Path("/user/idea/testdir/fileAction_file01_rename.txt")
        );

        // 3. 关闭资源
        fs.close();
    }

    // 查看文件信息
    @Test
    public void fileInfo() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 获取文件信息
        // 参数: 路径，是否递归获取所有信息
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();

            // 输出文件名称、长度、权限、分组
            System.out.println(status.getPath().getName());
            System.out.println(status.getLen());
            System.out.println(status.getPermission());
            System.out.println(status.getGroup());

            // 输出文件的块信息
            BlockLocation[] blockLocations = status.getBlockLocations();
            for (BlockLocation b : blockLocations) {
                String[] hosts = b.getHosts();

                // 输出文件的块所在的主机
                for (String host : hosts) {
                    System.out.println(host);
                }
            }

            System.out.println("-------------------------------------");
        }

        // 3. 关闭资源
        fs.close();
    }

    // 判断路径是文件还是文件夹
    @Test
    public void pathType() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 获取客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 获取路径信息
        FileStatus[] fileStatuses = fs.listStatus(new Path("/user/idea/testdir/"));
        for (FileStatus status : fileStatuses) {
            // 判断是文件还是目录
            if (status.isFile()) {
                System.out.println("file" + status.getPath().getName());
            } else {
                System.out.println("dir" + status.getPath().getName());
            }
        }

        // 3. 关闭资源
        fs.close();
    }
}
