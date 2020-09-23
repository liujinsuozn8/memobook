package com.ljs.myhd.mapreduce.zip.zipupzip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUnzip {
    public static void main(String[] args) throws IOException {
        decompress("G:/bigdatapackage/myhd/mapreduce/src/main/resources/com/ljs/myhd/mapreduce/zip/zipfile.txt.gz");

    }

    private static void decompress(String filename) throws IOException {
        // 根据文件的类型获取对应的压缩类
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(filename));

        // 如果没有对应的压缩类型则无法解压
        if (codec == null){
            System.out.println("can not decompress");
            return ;
        }

        // 获取输入流
        FileInputStream fis = new FileInputStream(filename);
        // 将输入流包装成压缩流
        CompressionInputStream cis = codec.createInputStream(fis);

        // 获取输出流
        FileOutputStream fos = new FileOutputStream(filename + ".decode");

        // 拷贝流
        IOUtils.copyBytes(cis, fos, 1024*1024, false);

        // 关闭资源
        IOUtils.closeStream(cis);
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
    }
}
