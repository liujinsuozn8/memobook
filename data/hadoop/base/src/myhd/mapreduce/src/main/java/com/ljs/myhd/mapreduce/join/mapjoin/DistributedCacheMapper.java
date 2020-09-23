package com.ljs.myhd.mapreduce.join.mapjoin;

import com.ljs.myhd.mapreduce.join.reducejoin.OrderTableBean;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DistributedCacheMapper extends Mapper<LongWritable, Text, OrderTableBean, NullWritable> {
    Map<String, String> productMap = new HashMap<>();
    OrderTableBean k = new OrderTableBean();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String productPath = context.getCacheFiles()[0].getPath().toString();
        BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(productPath), "UTF-8"));

        // 读取 product 文件中的数据，并转换为hashmap
        String line;
        while(StringUtils.isNotEmpty(line = fis.readLine())){
            String[] fields = line.split(" ");
            // 将数据保存到 hashmap 中
            productMap.put(fields[0],fields[1]);
        }

        fis.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");

        // orderid pid amount
        // 1001    01  1
        k.setOrderId(fields[0]);
        k.setpName(productMap.get(fields[1]));  // 从hashmap中获取name
        k.setAmount(Integer.parseInt(fields[2]));

        context.write(k, NullWritable.get());
    }
}
