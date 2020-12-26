package com.ljs.learn.hbasebase;

import java.io.IOException;
import java.util.HashMap;

// 插入数据
public class ScanTable {
    public static void main(String[] args) throws IOException {
        String tableName = "mydata:student";

        // 1. 检查是否存在
        if (HBaseUtils.isTableExist(tableName)) {

            HBaseUtils.scanTable(tableName);

        } else {
            System.out.println(tableName + " is not exist");
        }

        // 4. 关闭资源
        HBaseUtils.close();
    }
}

