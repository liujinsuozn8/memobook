package com.ljs.myhd.mapreduce.inputformat.combinetext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CombineWordCountDriver {
    // 测试数据: src\main\resources\com\ljs\myhd\mapreduce\inputformat\combinetext
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(CombineWordCountDriver.class);
        job.setMapperClass(CombineWordCountMapper.class);
        job.setReducerClass(CombineWordCountReducer.class);
        // 不使用 CombineTextInputFormat，将会出现7个 split

        // 使用 CombineTextInputFormat
        job.setInputFormatClass(CombineTextInputFormat.class);
        // 设置虚拟存储切片最大值 ： 4M
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println(result);
    }
}