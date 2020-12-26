package com.ljs.learn.hbasebase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;


public class HBaseUtils {
    private static Connection connection;
    private static Admin admin;

    static {
        // 1. 设置配置信息
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "zk01");

        // 2. 获取管理员对象
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 关闭资源
    public static void close() {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////////////////////////////////////////
    // DDL操作

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return 表是否存在
     * @throws IOException
     */
    public static boolean isTableExist(String tableName) throws IOException {
        // 检查表是否存在
        // 需要手动创建表对象
        TableName t = TableName.valueOf(tableName);
        boolean exist = admin.tableExists(t);

        // 返回结果
        return exist;
    }

    /**
     * 创建表
     *
     * @param tableName 表名
     * @param cfs       列族名
     */
    public static void createTable(String tableName, String... cfs) throws IOException {
        // 1. 判断是否传入了列族信息
        if (cfs.length <= 0) {
            System.out.println("not set the info of column family");
            return;
        }

        // 2. 判断表是否已经存在
        if (isTableExist(tableName)) {
            System.out.println(tableName + " is exist");
            return;
        }

        // 3. 创建表描述器
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        for (String cf : cfs) {
            // 4. 添加列族
            hTableDescriptor.addFamily(new HColumnDescriptor(cf));

        }
        // 5. 创建表
        admin.createTable(hTableDescriptor);
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public static void dropTable(String tableName) throws IOException {
        // 1. 如果表不存在，则异常高
        if (!isTableExist(tableName)) {
            System.out.println(tableName + " not exist");
            return;
        }

        TableName t = TableName.valueOf(tableName);

        // 2. 使表 disable
        admin.disableTable(t);

        // 3. 删除表
        admin.deleteTable(t);
    }

    /**
     * 创建命名空间
     *
     * @param name 命名空间名
     */
    public static void createNamespace(String name) {
        try {
            admin.createNamespace(NamespaceDescriptor.create(name).build());
        } catch (NamespaceExistException e) {
            System.out.println(name + " is exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////
    // DML 操作

    /**
     * 向表中插入数据
     *
     * @param tableName 表名
     * @param rowKey
     * @param family    列族
     * @param cells     数据  name:value
     */
    public static void putData(String tableName, String rowKey, String family, Map<String, String> cells) throws IOException {
        // 1. 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建put对象，需要将数据转换为字节数组
        Put put = new Put(Bytes.toBytes(rowKey));

        // 3. 给put对象赋值，插入多个列
        for (Map.Entry<String, String> entry : cells.entrySet()) {
            put.addColumn(
                    Bytes.toBytes(family),
                    Bytes.toBytes(entry.getKey()),
                    Bytes.toBytes(entry.getValue())
            );
        }

        // 4. 插入数据
        table.put(put);

        // 5. 关闭与表的连接
        table.close();
    }

    /**
     * 获取所有一个 rowkey 中的所有数据
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void getData(String tableName, String rowKey) throws IOException {
        // 1. 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建 get 对象
        Get get = new Get(Bytes.toBytes(rowKey));

        // 3. 获取数据
        Result result = table.get(get);

        // 4. 解析 result
        for (Cell cell : result.rawCells()) {
            // 输出数据
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String name = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            System.out.println("family=" + family + ", name=" + name + ", value=" + value);
        }

        // 关闭表连接
        table.close();
    }

    /**
     * 获取表中指定行、指定列族的数据
     * @param tableName
     * @param rowKey
     * @param family
     * @throws IOException
     */
    public static void getData(String tableName, String rowKey, String family) throws IOException {
        // 1. 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建 get 对象
        Get get = new Get(Bytes.toBytes(rowKey));
        // 设置列族
        get.addFamily(Bytes.toBytes(family));

        // 3. 获取数据
        Result result = table.get(get);

        // 4. 解析 result
        for (Cell cell : result.rawCells()) {
            // 输出数据
            String cellFamily = Bytes.toString(CellUtil.cloneFamily(cell));
            String name = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            System.out.println("family=" + cellFamily + ", name=" + name + ", value=" + value);
        }

        // 关闭表连接
        table.close();
    }

    /**
     * 获取表中指定行、指定列族、指定列的数据
     * @param tableName
     * @param rowKey
     * @param family
     * @throws IOException
     */
    public static void getData(String tableName, String rowKey, String family, String name) throws IOException {
        // 1. 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建 get 对象
        Get get = new Get(Bytes.toBytes(rowKey));
        // 设置列族和列名
        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(name));

        // 3. 获取数据
        Result result = table.get(get);

        // 4. 解析 result
        for (Cell cell : result.rawCells()) {
            // 输出数据
            String cellFamily = Bytes.toString(CellUtil.cloneFamily(cell));
            String cellName = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            System.out.println("family=" + cellFamily + ", name=" + cellName + ", value=" + value);
        }

        // 关闭表连接
        table.close();
    }

    /**
     * 获取所有一个 rowkey 中，指定 Version 数量的数据
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void getData(String tableName, String rowKey, int versions) throws IOException {
        // 1. 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建 get 对象
        Get get = new Get(Bytes.toBytes(rowKey));
        // 设置versions
        get.setMaxVersions(versions);

        // 3. 获取数据
        Result result = table.get(get);

        // 4. 解析 result
        for (Cell cell : result.rawCells()) {
            // 输出数据
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String name = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            System.out.println("family=" + family + ", name=" + name + ", value=" + value);
        }

        // 关闭表连接
        table.close();
    }


    /**
     * scan "Ns:tableName"
     * @param tableName
     */
    public static void scanTable(String tableName) throws IOException {
        // 1. 创建表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 2. 创建 scan 对象，空参构造器做全表扫描
        Scan scan = new Scan();

        // 3. 扫描表
        ResultScanner resultScanner = table.getScanner(scan);

        // 4. 解析结果
        for (Result result : resultScanner) {
            for (Cell cell : result.rawCells()) {
                // 输出数据
                String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String name = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));

                System.out.println("rowKey="+rowKey+", family=" + family + ", name=" + name + ", value=" + value);
            }
        }

        // 5. 关闭表连接
        table.close();
    }

    /**
     * 删除指定 rowkey下的数据
     * @param tableName
     * @param rowKwey
     * @throws IOException
     */
    public static void deleteData(String tableName, String rowKwey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKwey));

        table.delete(delete);
        table.close();
    }
}
