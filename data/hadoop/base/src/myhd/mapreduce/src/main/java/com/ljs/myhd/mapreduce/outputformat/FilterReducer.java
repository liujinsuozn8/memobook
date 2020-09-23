package com.ljs.myhd.mapreduce.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FilterReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
    Text k = new Text();
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        // 为文件内容附加换行符
        k.set(key.toString() + "\r\n");
        // 遍历当前key下的所有数据，防止有重复的情况
        for (NullWritable value : values) {
            context.write(k, NullWritable.get());
        }
    }
}
