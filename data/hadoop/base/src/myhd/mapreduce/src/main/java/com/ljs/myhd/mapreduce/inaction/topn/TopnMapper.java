package com.ljs.myhd.mapreduce.inaction.topn;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class TopnMapper extends Mapper<LongWritable, Text, TopnFlowBean, Text> {
    TreeMap<TopnFlowBean, Text> topmap = new TreeMap<>();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1 13736230513 192.196.123.1 www.abc.com 2481 24681 200
        String[] fields = value.toString().split(" ");
        Text v = new Text();

        TopnFlowBean bean = new TopnFlowBean();
        bean.setFlows(Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));

        v.set(fields[1]);
        topmap.put(bean, v);

        // 如果已经超过10个则删除最后一个
        if (topmap.size() > 10){
            topmap.remove(topmap.lastKey());
        }
    }

    // 整个切片处理完成后，在写出数据
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Map.Entry<TopnFlowBean, Text> n : topmap.entrySet()) {
            context.write(n.getKey(), n.getValue());
        }
    }
}
