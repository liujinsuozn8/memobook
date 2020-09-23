package com.ljs.myhd.mapreduce.inaction.topn;


import com.ljs.myhd.mapreduce.serialize.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 用于处理: src\main\resources\com\ljs\myhd\mapreduce\inaction\topn\phone_data.txt
// 获取流量最大的前10个数据
public class TopnDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(TopnDriver.class);
        job.setMapperClass(TopnMapper.class);
        job.setReducerClass(TopnReducer.class);

        job.setMapOutputKeyClass(TopnFlowBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TopnFlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println("result: " + result);
    }
}
