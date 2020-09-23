package com.ljs.myhd.mapreduce.join.reducejoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class OrderTableReducer extends Reducer<Text, OrderTableBean, OrderTableBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<OrderTableBean> values, Context context) throws IOException, InterruptedException {
        ArrayList<OrderTableBean> orders = new ArrayList<>();
        OrderTableBean pInfo = new OrderTableBean();

        // 划分数据
        for (OrderTableBean bean : values) {
            if ("order".equals(bean.getFlag())){
                OrderTableBean tmp = new OrderTableBean();

                try {
                    BeanUtils.copyProperties(tmp, bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                orders.add(tmp);
            } else {
                try {
                    BeanUtils.copyProperties(pInfo, bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        // 连接数据
        for (OrderTableBean o : orders) {
            o.setpName(pInfo.getpName());
            context.write(o, NullWritable.get());
        }
    }
}
