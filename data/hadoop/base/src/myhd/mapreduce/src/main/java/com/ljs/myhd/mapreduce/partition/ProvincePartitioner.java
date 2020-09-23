package com.ljs.myhd.mapreduce.partition;

import com.ljs.myhd.mapreduce.serialize.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

// 用于处理: src\main\resources\com\ljs\myhd\mapreduce\partition\phone_data.txt
// 使用： src\main\java\com\ljs\myhd\mapreduce\serialize\FlowBean.java 的处理逻辑
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {

    // 在 Mapper 将数据通过 context.write 写出后，会经过 partition 进行分区运算
    // 根据需求自定义运算规则
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        // 自定义规则: 按照手机号前3位输出
           String num = text.toString().substring(0, 3);

        // 5种分区，需要指定数量相同的 reducer 数量
        switch (num) {
            case "136":
                return 0;  // 分区值应该从 0 开始
            case "137":
                return 1;
            case "138":
                return 2;
            case "139":
                return 3;
            default:
                return 4;
        }
    }
}
