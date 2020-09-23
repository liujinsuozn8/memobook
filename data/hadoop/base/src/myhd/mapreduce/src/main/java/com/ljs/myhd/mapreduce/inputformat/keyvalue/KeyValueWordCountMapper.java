package com.ljs.myhd.mapreduce.inputformat.keyvalue;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// 使用key value的方式处理
public class KeyValueWordCountMapper extends Mapper<Text, Text, Text, LongWritable> {
    LongWritable v = new LongWritable(1);
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key, v);
    }
}
