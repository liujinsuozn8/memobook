package com.ljs.myhd.mapreduce.compare.grouping;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderGroupingComparator extends WritableComparator {
    public OrderGroupingComparator() {
        super(OrderBean.class, true);
    }
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        // 将输入参数强转为 OrderBean类型
        OrderBean beanA = (OrderBean) a;
        OrderBean beanB = (OrderBean) b;

        // 比较
        if (beanA.getOrderId() > beanB.getOrderId()){
            return 1; // 上移
        } else if (beanA.getOrderId() < beanB.getOrderId()){
            return -1; // 下移
        } else{
            return  0; // 相同则输出到同一个Reducer
        }
    }
}
