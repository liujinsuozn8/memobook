package com.ljs.myhd.mapreduce.inaction.topn;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

// 全排序
public class TopnFlowBean implements WritableComparable<TopnFlowBean> {

    private long downFlow;
    private long upFlow;
    private long sumFlow;

    public TopnFlowBean() {
        super();
    }

    public void setFlows(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        sumFlow = upFlow + downFlow;
    }

    @Override
    public int compareTo(TopnFlowBean o) {
        // 按照 sumFlow 倒序排列
        if (sumFlow > o.getSumFlow()) {
            return -1;
        } else if (sumFlow < o.getSumFlow()) {
            return 1;
        } else {
            return 0;
        }
    }

    // 序列化
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    // 反序列化
    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    // 输出内容
    @Override
    public String toString() {
        return downFlow + " " + upFlow + " " + sumFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }
}
