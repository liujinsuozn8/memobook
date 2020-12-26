package com.ljs.learn.hbasebase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * 判断表是否存在，新API
 */
public class TableExistWithNewAPI {
    // 1. 判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        // 1. 设置配置信息
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "zk01");

        // 2. 获取管理员对象
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        // 3. 检查表是否存在
        // 需要手动创建表对象
        TableName t = TableName.valueOf(tableName);
        boolean exist = admin.tableExists(t);

        // 4. 关闭资源
        admin.close();

        // 5. 返回结果
        return exist;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(isTableExist("mydata:student"));
        System.out.println(isTableExist("student2"));
        System.out.println(isTableExist("xxxxx"));
    }
}
