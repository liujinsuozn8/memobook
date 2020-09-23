package com.ljs.myhd.mapreduce.serialize;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    FlowBean v = new FlowBean();
    long sumUpFlow;
    long sumDownFlow;
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        // 1. 求和
         sumUpFlow =0;
         sumDownFlow =0;

        for (FlowBean b : values) {
            sumUpFlow += b.getUpFlow();
            sumDownFlow += b.getDownFlow();
        }
        v.setFlows(sumUpFlow, sumUpFlow);

        // 2. 写出
        context.write(key, v);
    }
}
