package com.ljs.learn.hbasebase;

import java.io.IOException;
import java.util.HashMap;

// 获取数据
public class GetTableData {
    public static void main(String[] args) throws IOException {
        String tableName = "mydata:student";

        // 1. 检查是否存在
        if (HBaseUtils.isTableExist(tableName)) {
            // 2.1 获取rowKey下的所有数据
            System.out.println("rowKey=0001");
            HBaseUtils.getData(tableName, "0001");

            // 2.2 获取rowKey、family 下的所有数据
            System.out.println("");
            System.out.println("rowKey=0001, family=info1");
            HBaseUtils.getData(tableName, "0001", "info1");

            // 2.3 获取rowKey、family、name 下的所有数据
            System.out.println("");
            System.out.println("rowKey=0001, family=info1, name=name");
            HBaseUtils.getData(tableName, "0001", "info1", "name");

            // 2.4 获取rowKey下，指定 version 数量的数据
            System.out.println("");
            System.out.println("rowKey=0001, version=3");
            HBaseUtils.getData(tableName, "0001", 3);

        } else {
            System.out.println(tableName + " is not exist");
        }

        // 2. 关闭资源
        HBaseUtils.close();
    }
}

