package com.ljs.myhd.mapreduce.inputformat.customize;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SequenceFileReducer extends Reducer<Text, BytesWritable, Text, BytesWritable> {
    // 每次传输的只有一个文件的数据，所以 values 中只有一份数据
    @Override
    protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
        BytesWritable fileData = values.iterator().next();
        context.write(key, fileData);
    }
}
