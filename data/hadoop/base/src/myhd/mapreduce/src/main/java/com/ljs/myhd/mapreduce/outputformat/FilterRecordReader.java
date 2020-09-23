package com.ljs.myhd.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class FilterRecordReader extends RecordWriter<Text, NullWritable> {
    FSDataOutputStream fo01;
    FSDataOutputStream fo02;
    public FilterRecordReader(TaskAttemptContext job) {
        // 获取文件系统
        FileSystem fs;
        try {
            fs = FileSystem.get(job.getConfiguration());

            // 创建输出流
//            G:\bigdatapackage\myhd\mapreduce
            fo01 = fs.create(new Path("mapreduce/src/main/resources/com/ljs/myhd/mapreduce/outputformat/result/fo01.txt"));
            fo02 = fs.create(new Path("mapreduce/src/main/resources/com/ljs/myhd/mapreduce/outputformat/result/fo02.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String line = key.toString();
        // 匹配数据，并输出到不同的文件中
        if(line.contains("www.abc.com")){
            fo01.write(line.getBytes());
        } else {
            fo02.write(line.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        // 关闭流
        IOUtils.closeStream(fo01);
        IOUtils.closeStream(fo02);
    }
}
