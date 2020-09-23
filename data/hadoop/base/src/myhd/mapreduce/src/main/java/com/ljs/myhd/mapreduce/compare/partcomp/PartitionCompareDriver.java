package com.ljs.myhd.mapreduce.compare.partcomp;

import com.ljs.myhd.mapreduce.compare.allcompare.AllCompareFlowBean;
import com.ljs.myhd.mapreduce.compare.allcompare.AllCompareFlowMapper;
import com.ljs.myhd.mapreduce.compare.allcompare.AllCompareFlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

// 在全排序的基础上添加分区，完成分区内排序
public class PartitionCompareDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(PartitionCompareDriver.class);
        job.setMapperClass(AllCompareFlowMapper.class);
        job.setReducerClass(AllCompareFlowReducer.class);

        job.setMapOutputKeyClass(AllCompareFlowBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(AllCompareFlowBean.class);

        // 设置分区
        job.setPartitionerClass(CompareProvincePartitioner.class);
        // 设置 reducer 的数量
        job.setNumReduceTasks(5);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean result = job.waitForCompletion(true);

        System.out.println("result=" + result);
    }
}