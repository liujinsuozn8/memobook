package com.ljs.myhd.mapreduce.join.reducejoin;

import com.ljs.myhd.mapreduce.compare.grouping.OrderMapper;
import com.ljs.myhd.mapreduce.compare.grouping.OrderReducer;
import com.ljs.myhd.mapreduce.wordcount.WordCountDriver;
import com.ljs.myhd.mapreduce.wordcount.WordCountMapper;
import com.ljs.myhd.mapreduce.wordcount.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 用于处理
// src\main\resources\com\ljs\myhd\mapreduce\join\reducejoin\order.txt
// src\main\resources\com\ljs\myhd\mapreduce\join\reducejoin\product.txt
public class OrderTableDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置 Driver 的 jar 的存储位置（通过反射）
        job.setJarByClass(OrderTableDriver.class);
        job.setMapperClass(OrderTableMapper.class);
        job.setReducerClass(OrderTableReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(OrderTableBean.class);

        job.setOutputKeyClass(OrderTableBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println(result);
    }
}
