package com.ljs.myhd.mapreduce.multijob.job02;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Job02Reducer extends Reducer<Text,Text, Text, Text> {
    Text v = new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (Text value : values) {
            sb.append(value.toString() + " ");
        }

        v.set(sb.toString().trim());
        context.write(key, v);
    }
}
