package com.ljs.myhd.mapreduce.join.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class OrderTableMapper extends Mapper<LongWritable, Text, Text, OrderTableBean> {
    String fileName;
    Text k = new Text();
    OrderTableBean v = new OrderTableBean();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取切片信息
        FileSplit split = (FileSplit) context.getInputSplit();
        fileName = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");

        if (fileName.startsWith("order")) {
            // id  pid amount
            // 1001 01 1
            // 将 pid 作为key，来分组结合
            k.set(fields[1]);

            v.setOrderId(fields[0]);
            v.setpId(fields[1]);
            v.setpName("");
            v.setAmount(Integer.parseInt(fields[2]));
            v.setFlag("order");
        } else {
            // pid pname
            // 01 paa
            // 将 pid 作为key，来分组结合
            k.set(fields[0]);

            v.setOrderId("");
            v.setpId("");
            v.setpName(fields[1]);
            v.setAmount(0);
            v.setFlag("product");
        }
        context.write(k, v);
    }
}
