package com.ljs.learn.hbasebase;

import java.io.IOException;

public class CreateTable {
    public static void main(String[] args) throws IOException {
        // 1. 检查是否存在
        System.out.println(HBaseUtils.isTableExist("person"));

        // 2. 创建表
        HBaseUtils.createTable("person", "info1", "info2");

        // 3. 检查是否创建成功
        System.out.println(HBaseUtils.isTableExist("person"));

        // 4. 关闭资源
        HBaseUtils.close();
    }
}

