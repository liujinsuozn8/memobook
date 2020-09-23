package com.ljs.myhd.mapreduce.join.mapjoin;

import com.ljs.myhd.mapreduce.join.reducejoin.OrderTableBean;
import com.ljs.myhd.mapreduce.join.reducejoin.OrderTableDriver;
import com.ljs.myhd.mapreduce.join.reducejoin.OrderTableMapper;
import com.ljs.myhd.mapreduce.join.reducejoin.OrderTableReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// 用于处理:

public class DistributedCacheDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(OrderTableDriver.class);
        job.setMapperClass(DistributedCacheMapper.class);
        job.setNumReduceTasks(0);

        job.setOutputKeyClass(OrderTableBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 加载缓存数据
        job.addCacheFile(new URI("file:///G:/bigdatapackage/myhd/mapreduce/src/main/resources/com/ljs/myhd/mapreduce/join/mapjoin/product/product.txt"));

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        System.out.println(result);
    }
}
