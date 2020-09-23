package com.ljs.myhd.mapreduce.serialize;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    /*
      数据格式:
      1    13736230513 192.196.123.1  www.abc.com  2481    24681    200
      编号  id          地址            请求地址      上传流量  下载流量  http状态码
      0    1            2             3             4       5       6
     */

    // key、value
    Text k = new Text();
    FlowBean v = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1. 获取一行数据，并用空格分割
        String[] splits = value.toString().split(" ");

        // 2. 设置 key、value
        k.set(splits[1]);
        v.setFlows(Long.parseLong(splits[4]), Long.parseLong(splits[5]));

        // 3. 写出
        context.write(k, v);
    }
}
