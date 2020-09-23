package com.ljs.myhd.mapreduce.inputformat.customize;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

// BytesWritable 用于保存整个文件的数据
public class WholeFileFormat extends FileInputFormat<Text, BytesWritable> {
    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        WholeRecordReader reader = new WholeRecordReader();
        reader.initialize(split, context);

        return reader;
    }
}
