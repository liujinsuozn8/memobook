package com.ljs.myhd.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// LongWritable, Text,           Text,           IntWritable
// 字符偏移量      文本中的一行内容   处理后的字符内容   数量(此处默认为1)
// 输入的key      输入的value       输出的key        输出的value
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text k = new Text();
    IntWritable v =  new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1. 获取一行： 222 333 444 555
        String line = value.toString();

        // 2. 用空格分割字符串: [222, 333, 444, 555]
        String[] words = line.split("\\s+");

        // 3. 循环输出
        for (String word : words) {
            k.set(word);
            context.write(k,v);
        }
    }
}
