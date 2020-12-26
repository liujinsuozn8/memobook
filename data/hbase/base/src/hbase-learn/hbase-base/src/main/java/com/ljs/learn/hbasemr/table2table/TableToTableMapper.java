package com.ljs.learn.hbasemr.table2table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


// 将表A的info1:name 插入到表B
public class TableToTableMapper extends TableMapper<ImmutableBytesWritable, Put> {
    // ImmutableBytesWritable key 就是 rowkey
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        Put put = new Put(key.get());

        // 获取数据
        for (Cell cell : value.rawCells()) {

            if ("info1".equals(Bytes.toString(CellUtil.cloneFamily(cell))) &&
                    "name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                put.add(cell);
                break;
            }
        }

        // 写出
        context.write(key, put);
    }
}
