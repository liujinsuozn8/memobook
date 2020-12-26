package com.ljs.learn.hbasebase;

import java.io.IOException;
import java.util.HashMap;

// 插入数据
public class PutTableData {
    public static void main(String[] args) throws IOException {
        String tableName = "mydata:student";

        // 1. 检查是否存在
        if (HBaseUtils.isTableExist(tableName)) {
            HashMap<String, String> cells = new HashMap<>();

            cells.put("name", "st01");
            cells.put("sex", "male");
            cells.put("age", "11");
            // 2. 插入数据
            HBaseUtils.putData(tableName, "0001", "info1", cells);

        } else {
            System.out.println(tableName + " is not exist");
        }

        // 4. 关闭资源
        HBaseUtils.close();
    }
}

