package com.ljs.myhd.mapreduce.zip.mrzip;

import com.ljs.myhd.mapreduce.wordcount.WordCountDriver;
import com.ljs.myhd.mapreduce.wordcount.WordCountMapper;
import com.ljs.myhd.mapreduce.wordcount.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MRZipDriver {
    // 测试数据: src\main\resources\com\ljs\myhd\mapreduce\mr_file01.txt
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        // 开启 map 输出压缩
        conf.setBoolean("mapreduce.map.output.compress", true);
        // 设置 map 输出的压缩格式
        conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);

        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 开启 reduce 输出压缩
        // 设置 reduce 输出的压缩格式
        FileOutputFormat.setCompressOutput(job, true);
        FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);

        boolean result = job.waitForCompletion(true);

        System.out.println(result);
    }
}
