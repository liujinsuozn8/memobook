package com.ljs.myhd.mapreduce.partition;

import com.ljs.myhd.mapreduce.serialize.FlowBean;
import com.ljs.myhd.mapreduce.serialize.FlowMapper;
import com.ljs.myhd.mapreduce.serialize.FlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 用于处理: src\main\resources\com\ljs\myhd\mapreduce\partition\phone_data.txt
// 使用： src\main\java\com\ljs\myhd\mapreduce\serialize\FlowBean.java 的处理逻辑
public class ProvinceFlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(ProvinceFlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置分区方式
        job.setPartitionerClass(ProvincePartitioner.class);
        // 设置与分区数量相同的 reducer
        job.setNumReduceTasks(5);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println("result=" + result);
    }
}
