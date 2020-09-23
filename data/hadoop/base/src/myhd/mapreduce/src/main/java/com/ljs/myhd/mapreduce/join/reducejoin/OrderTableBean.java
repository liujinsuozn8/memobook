package com.ljs.myhd.mapreduce.join.reducejoin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderTableBean implements Writable {
    private String orderId; // 订单号
    private String pId;     // 产品号
    private int amount;     // 数量
    private String pName;   // 产品名
    private String flag;    // 数据来源

    public OrderTableBean() {
        super();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(pId);
        out.writeInt(amount);
        out.writeUTF(pName);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readUTF();
        pId = in.readUTF();
        amount = in.readInt();
        pName = in.readUTF();
        flag = in.readUTF();
    }

    @Override
    public String toString() {
        return  orderId + " " + pName + " " + amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
