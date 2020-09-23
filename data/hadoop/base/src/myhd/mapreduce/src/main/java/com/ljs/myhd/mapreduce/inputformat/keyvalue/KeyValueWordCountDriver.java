package com.ljs.myhd.mapreduce.inputformat.keyvalue;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 测试数据: src\main\resources\com\ljs\myhd\mapreduce\inputformat\keyvalue\mr_file01.txt
public class KeyValueWordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        // 设置分隔符，覆盖 KeyValueTextInputFormat 默认的 \t 分隔符
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, " ");
        Job job = Job.getInstance(conf);

        job.setJarByClass(KeyValueWordCountDriver.class);
        job.setMapperClass(KeyValueWordCountMapper.class);
        job.setReducerClass(KeyValueWordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 设置format
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        boolean result = job.waitForCompletion(true);
        System.out.println("result=" + result);
    }
}