package com.ljs.learn.hbaseweibo.utils;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

import static com.ljs.learn.hbaseweibo.constants.Constants.CONF;

public class HBaseUtils {
    /**
     * 1. 创建命名空间
     * 2. 判断表是否存在
     * 3. 创建表
     */

    /**
     * 创建命名空间
     * @param nameSpace
     * @throws IOException
     */
    public static void createNameSpace(String nameSpace) throws IOException {
        // 1. 获取connection、admin对象
        Connection connection = ConnectionFactory.createConnection(CONF);
        Admin admin = connection.getAdmin();

        // 2. 创建命名空间描述器
        NamespaceDescriptor nsdesc = NamespaceDescriptor.create(nameSpace).build();

        // 3. 创建命名空间
        admin.createNamespace(nsdesc);

        // 4. 关闭资源
        admin.close();
        connection.close();
    }

    /**
     * 检查表是否存在，只在创建表时，检查表是否存在，所以设置为 private
     * @param tableName
     * @return
     * @throws IOException
     */
    private static boolean isTableExist(String tableName) throws IOException {
        // 1. 获取connection、admin对象
        Connection connection = ConnectionFactory.createConnection(CONF);
        Admin admin = connection.getAdmin();

        // 2. 检查表是否存在
        boolean result = admin.tableExists(TableName.valueOf(tableName));

        // 3. 关闭资源
        admin.close();
        connection.close();

        // 4. 返回结果
        return result;
    }

    /**
     * 创建表
     * @param tableName
     * @param versions
     * @param familys
     */
    public static void createTable(String tableName, int versions, String ...familys) throws IOException {
        // 1. 判断是否传了列族
        if (familys.length <= 0){
            System.out.println("not set family of table");
            return ;
        }

        // 2. 判断表是否存在
        if (isTableExist(tableName)){
            System.out.println("table " + tableName + " is exist");
            return;
        }

        // 3. 获取connection、admin对象
        Connection connection = ConnectionFactory.createConnection(CONF);
        Admin admin = connection.getAdmin();

        // 4. 创建表信息
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String family : familys) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
            hColumnDescriptor.setMaxVersions(versions);
            tableDescriptor.addFamily(hColumnDescriptor);
        }

        // 5. 创建表
        admin.createTable(tableDescriptor);

        // 6. 关闭资源
        admin.close();
        connection.close();
    }
}
