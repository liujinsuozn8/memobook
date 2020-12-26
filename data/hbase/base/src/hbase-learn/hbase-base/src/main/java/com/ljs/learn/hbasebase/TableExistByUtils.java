package com.ljs.learn.hbasebase;

import java.io.IOException;

/**
 * 判断表是否存在，新API，使用工具
 */
public class TableExistByUtils {
    public static void main(String[] args) throws IOException {
        System.out.println(HBaseUtils.isTableExist("mydata:student"));
        System.out.println(HBaseUtils.isTableExist("student2"));
        System.out.println(HBaseUtils.isTableExist("xxxxx"));

        // 关闭资源
        HBaseUtils.close();
    }
}
