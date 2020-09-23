package com.ljs.myhd.mapreduce.inaction.topn;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class TopnReducer extends Reducer<TopnFlowBean, Text, Text, TopnFlowBean> {
    // 只启动1个reducer，可以将数据都保存在 topmap中
    TreeMap<TopnFlowBean, Text> topnmap = new TreeMap<>();
    @Override
    protected void reduce(TopnFlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            // 拷贝 bean 数据
            TopnFlowBean bean = new TopnFlowBean();
            bean.setFlows(key.getUpFlow(), key.getDownFlow());

            // 将数据添加到 map 中
            topnmap.put(bean, new Text(value));

            // 删除多余的数据
            if (topnmap.size() > 10){
                topnmap.remove(topnmap.lastKey());
            }
        }
    }

    // 结束时，将前n个数据写出
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Map.Entry<TopnFlowBean, Text> n : topnmap.entrySet()) {
            context.write(n.getValue(), n.getKey());
        }
    }
}
