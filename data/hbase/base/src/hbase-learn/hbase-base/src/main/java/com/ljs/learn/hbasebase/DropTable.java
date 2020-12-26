package com.ljs.learn.hbasebase;

import java.io.IOException;

public class DropTable {
    public static void main(String[] args) throws IOException {
        // 1. 检查是否存在
        System.out.println(HBaseUtils.isTableExist("person"));

        // 2. 删除表
        HBaseUtils.dropTable("person");

        // 3. 检查是否删除成功
        System.out.println(HBaseUtils.isTableExist("person"));

        // 4. 关闭资源
        HBaseUtils.close();
    }
}

