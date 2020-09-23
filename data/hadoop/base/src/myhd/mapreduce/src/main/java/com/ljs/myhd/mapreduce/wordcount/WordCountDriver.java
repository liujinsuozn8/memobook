package com.ljs.myhd.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {
    // 测试数据: src\main\resources\com\ljs\myhd\mapreduce\mr_file01.txt
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1. 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置 Driver 的 jar 的存储位置（通过反射）
        job.setJarByClass(WordCountDriver.class);

        // 3. 关联 Mapper、Reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4. 设置 Mapper 阶段输出的数据的 key、value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5. 设置最终输出的key、value类型
        // 有不是reducer的情况，所以是output
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6. 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7. 提交 job，输出信息
        boolean result = job.waitForCompletion(true);

        // 8. 输出执行是否成功
        System.out.println(result);
    }
}