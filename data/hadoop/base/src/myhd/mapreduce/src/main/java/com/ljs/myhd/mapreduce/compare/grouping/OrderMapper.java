package com.ljs.myhd.mapreduce.compare.grouping;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {
    OrderBean k = new OrderBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");

        k.setOrderId(Integer.parseInt(fields[0]));
        k.setPrice(Double.parseDouble(fields[2]));

        context.write(k, NullWritable.get());
    }
}
