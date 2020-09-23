package com.ljs.myhd.mapreduce.inputformat.customize;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

// 【每个切片都会创建一个 RecordReader 对象】
// 一次读取整个文件，不进行分块
// key = path
// value = 文件内容的二进制数据
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {
    private FileSplit split;
    private Configuration conf;
    private BytesWritable v = new BytesWritable();
    private Text k = new Text();
    // 用于判断当前文件是否已经读取过
    // 因为Mapper中通过 `while(context.nextKeyValue())` 的方式读取
    // 第一次必须要有一个 true 标记，使文件可以被读取。读取之后再设置为false，不可读取
    // 一共会读取两次
    private boolean canRead = true;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        // 保存切片信息
        this.split = (FileSplit) split;
        // 从上下文中获取配置信息，并保存
        conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (canRead) {
            // 读取整个文件
            // 从切片中获取文件路径
            Path path = split.getPath();

            // 通过路径对象获取文件系统对象
            FileSystem fs = path.getFileSystem(conf);

            // 获取文件的输入流
            FSDataInputStream fis = fs.open(path);
            byte[] buf = new byte[(int) split.getLength()];
            // 读取文件
            IOUtils.readFully(fis, buf, 0, buf.length);

            // 设置 key value，并保存在实例对象内部
            v.set(buf, 0, buf.length);
            k.set(path.toString());

            IOUtils.closeStream(fis);
            canRead = false;
            return true;
        }

        return false;
    }

    // 返回上一次调用 nextKeyValue 时，保存的数据
    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    // 返回上一次调用 nextKeyValue 时，保存的数据
    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
