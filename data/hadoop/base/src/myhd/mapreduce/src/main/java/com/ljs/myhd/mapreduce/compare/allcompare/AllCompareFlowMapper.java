package com.ljs.myhd.mapreduce.compare.allcompare;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// 为了使 FlowBean 中的 sumFlow 参与排序，在写出时，需要将 FlowBean作为bean，
public class AllCompareFlowMapper extends Mapper<LongWritable, Text, AllCompareFlowBean, Text> {
    private AllCompareFlowBean k = new AllCompareFlowBean();
    private Text v = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");

        k.setFlows(Long.parseLong(fields[4]), Long.parseLong(fields[5]));
        v.set(fields[1]);

        context.write(k, v);
    }
}
