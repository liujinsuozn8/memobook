package com.ljs.myhd.mapreduce.compare.allcompare;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AllCompareFlowReducer extends Reducer<AllCompareFlowBean, Text, Text, AllCompareFlowBean> {
    @Override
    protected void reduce(AllCompareFlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, key);
        }
    }
}
