package com.ljs.myhd.mapreduce.compare.partcomp;

import com.ljs.myhd.mapreduce.compare.allcompare.AllCompareFlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

// flow数据为key，phone_num数据为value，所以需要通过 value 来判断分区
public class CompareProvincePartitioner extends Partitioner<AllCompareFlowBean, Text> {
    @Override
    public int getPartition(AllCompareFlowBean allCompareFlowBean, Text text, int numPartitions) {
        String num = text.toString().substring(0, 3);
        switch (num){
            case "136":
                return 0;
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
