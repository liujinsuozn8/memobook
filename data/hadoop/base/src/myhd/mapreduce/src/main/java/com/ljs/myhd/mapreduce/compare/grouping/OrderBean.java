package com.ljs.myhd.mapreduce.compare.grouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {
    private int orderId;
    private double price;

    public OrderBean() {
        super();
    }

    @Override
    public int compareTo(OrderBean o) {
        // 商品id升序排列，价格降序排列
        if(orderId > o.getOrderId()){
            return 1;
        }else if (orderId < o.getOrderId()){
            return -1;
        } else {
            return price > o.getPrice() ? -1:1;
        }
    }

    // 序列化
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(orderId);
        out.writeDouble(price);
    }

    // 反序列化
    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readInt();
        price = in.readDouble();
    }

    // 设置输出内容


    @Override
    public String toString() {
        return orderId + " " + price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
