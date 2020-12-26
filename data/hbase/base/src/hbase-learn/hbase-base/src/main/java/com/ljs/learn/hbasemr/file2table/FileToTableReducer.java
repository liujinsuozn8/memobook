package com.ljs.learn.hbasemr.file2table;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

// 数据格式：rowkey family name value
public class FileToTableReducer extends TableReducer<LongWritable, Text, NullWritable> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            // 1. 切分字符串
            String[] fields = value.toString().split(" ");

            // 2. 创建 Put 对象
            Put put = new Put(Bytes.toBytes(fields[0]));
            put.addColumn(
                    Bytes.toBytes(fields[1]),
                    Bytes.toBytes(fields[2]),
                    Bytes.toBytes(fields[3])
            );

            // 3. 写出
            context.write(NullWritable.get(), put);
        }
    }
}
