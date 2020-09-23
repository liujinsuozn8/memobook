package com.ljs.myhd.mapreduce.multijob.job02;

import com.ljs.myhd.mapreduce.multijob.job01.Job01Driver;
import com.ljs.myhd.mapreduce.multijob.job01.Job01Mapper;
import com.ljs.myhd.mapreduce.multijob.job01.Job01Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 用于处理job01的结果

public class Job02Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(Job02Driver.class);
        job.setMapperClass(Job02Mapper.class);
        job.setReducerClass(Job02Reducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.out.println("result=" + result);
    }
}
