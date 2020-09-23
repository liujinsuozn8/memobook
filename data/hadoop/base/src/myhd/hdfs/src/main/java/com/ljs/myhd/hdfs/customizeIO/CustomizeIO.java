package com.ljs.myhd.hdfs.customizeIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

// 自定义IO，本质上就是 输入流、输出流之间的对拷
public class CustomizeIO {
    // 上传文件
    @Test
    public void upload() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 创建流
        // 获取输入流，本地路径
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("com/ljs/myhd/hdfs/customizeIO/customizeIO_file01.txt");
        // 获取输出流，hdfs路径
        FSDataOutputStream fos = fs.create(new Path("/user/input/idea/customizeIO_file01.txt"));

        // 3. 流的拷贝
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);

        // 5. 关闭资源
        fs.close();
    }

    // 下载文件
    @Test
    public void download() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 创建流
        // 获取输入流，hdfs路径
        FSDataInputStream fis = fs.open(new Path("/user/input/idea/customizeIO_file01.txt"));
        // 获取输出流，本地路径
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/customizeIO_file01_download.txt");

        // 3. 流的拷贝
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);

        // 5. 关闭资源
        fs.close();
    }

    // 操作前，需要上传 hadoop 的压缩包，用于操作
    // 定位读取，读取第一部分
    @Test
    public void downloadPart1() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 创建流
        // 获取输入流，hdfs路径
        FSDataInputStream fis = fs.open(new Path("/user/input/idea/hadoop-2.7.2.tar.gz"));
        // 获取输出流，本地路径
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/hadoop-2.7.2.tar.gz-part01");

        // 3. 流的拷贝
        // 创建缓冲区
        byte[] buf = new byte[1024];

        // 只读取1块，从头开始读取，读取 128M 的内容
        for (int i = 0; i < 1024 * 128; i++) {
            fis.read(buf);
            fos.write(buf);
        }

        // 4. 关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);

        // 5. 关闭资源
        fs.close();
    }

    // 操作前，需要上传 hadoop 的压缩包，用于操作
    // 定位读取，读取第二部分
    @Test
    public void downloadPart2() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 创建流
        // 获取输入流，hdfs路径
        FSDataInputStream fis = fs.open(new Path("/user/input/idea/hadoop-2.7.2.tar.gz"));
        // 获取输出流，本地路径
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/hadoop-2.7.2.tar.gz-part02");

        // 3. 流的拷贝
        // 跳过第1块，即前128M
        fis.seek(1024*1024*128);

        // 读取剩余的所有数据
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);

        // 5. 关闭资源
        fs.close();
    }
}
