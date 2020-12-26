package com.ljs.learn.hbasebase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * 判断表是否存在，旧API
 */
public class TableExist {
    // 判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        // 1. 设置配置信息
        HBaseConfiguration conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum", "zk01");

        // 2. 获取管理员对象
        HBaseAdmin admin = new HBaseAdmin(conf);

        // 3. 检查表是否存在
        boolean exist = admin.tableExists(tableName);

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
