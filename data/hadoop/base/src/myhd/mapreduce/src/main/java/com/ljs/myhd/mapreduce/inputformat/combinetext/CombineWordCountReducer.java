package com.ljs.myhd.mapreduce.inputformat.combinetext;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

// Text,            IntWritable,      Text, IntWritable
// mapper输出的key   mapper输出的value  word  word的数量
public class CombineWordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    int sum;
    IntWritable v = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 1. 求和
        sum = 0;
        for (IntWritable count : values) {
            sum+=count.get();
        }

        // 2. 输出
        v.set(sum);
        context.write(key, v);
    }
}
