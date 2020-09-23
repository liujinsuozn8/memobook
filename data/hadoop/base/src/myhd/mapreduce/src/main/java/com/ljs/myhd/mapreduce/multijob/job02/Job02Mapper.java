package com.ljs.myhd.mapreduce.multijob.job02;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Job02Mapper extends Mapper<LongWritable, Text, Text, Text> {
    Text k = new Text();
    Text v = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("--");
        k.set(fields[0]);

        v.set(StringUtils.join(fields[1].split("\t"), "-->"));

        context.write(k, v);
    }
}
