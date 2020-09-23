package com.ljs.myhd.mapreduce.zip.zipupzip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;

// 文件压缩
public class FIleZip {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        compress(
                "G:/bigdatapackage/myhd/mapreduce/src/main/resources/com/ljs/myhd/mapreduce/zip/zipfile.txt",
//                "org.apache.hadoop.io.compress.BZip2Codec"
                "org.apache.hadoop.io.compress.GzipCodec"
        );
    }

    private static void compress(String filename, String method) throws IOException, ClassNotFoundException {
        // 获取压缩工具类
        Class codecClass = Class.forName(method);
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, new Configuration());

        // 获取文件的输入流
        FileInputStream fis = new FileInputStream(filename);

        // 获取输出流并转换为压缩流，`原始文件名 + 压缩后缀` 作为新的文件名
        FileOutputStream fos = new FileOutputStream(filename + codec.getDefaultExtension());
        CompressionOutputStream cos = codec.createOutputStream(fos);

        // 拷贝流
        IOUtils.copyBytes(fis, cos, 1024*1024, false);

        // 关闭资源
        // 必须先关闭 压缩流
        IOUtils.closeStream(cos);
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
    }
}
