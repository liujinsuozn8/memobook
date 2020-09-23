package com.ljs.myhd.mapreduce.multijob.job01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class Job01Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    String fileName;
    IntWritable v = new IntWritable(1);
    Text k = new Text();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit split = (FileSplit) context.getInputSplit();
        this.fileName = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(" ");

        for (String word : words) {
            k.set(word + "--" + fileName);
            context.write(k, v);
        }
    }
}
