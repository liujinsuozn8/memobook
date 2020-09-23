package com.ljs.myhd.mapreduce.serialize;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

// 用于处理: src\main\resources\com\ljs\myhd\mapreduce\serialize\phone_data.txt 中的数据
public class FlowBean implements Writable {
    // 1. 定义属性，用于保存每一行分解后的数据
    private long upFlow;        // 上传流量
    private long downFlow;      // 下载流量
    private long sumFlow;       // 总流量

    // 2. 需要重写空参构造器，在反序列化时需要使用
    public FlowBean() {
        super();
    }

    // 3. 同时设置三个属性
    public void setFlows(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    // 4. 序列化方法
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    // 5. 反序列化方法
    @Override
    public void readFields(DataInput in) throws IOException {
        // 需要和序列化方法顺序一致
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    // 6. 重写 toString 方法，用于将结果输出到文件
    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }
}
