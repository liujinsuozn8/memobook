package com.ljs.myhd.mapreduce.compare.allcompare;

import com.ljs.myhd.mapreduce.partition.ProvinceFlowDriver;
import com.ljs.myhd.mapreduce.partition.ProvincePartitioner;
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

public class AllCompareFlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(AllCompareFlowDriver.class);
        job.setMapperClass(AllCompareFlowMapper.class);
        job.setReducerClass(AllCompareFlowReducer.class);

        job.setMapOutputKeyClass(AllCompareFlowBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(AllCompareFlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println("result=" + result);
    }
}