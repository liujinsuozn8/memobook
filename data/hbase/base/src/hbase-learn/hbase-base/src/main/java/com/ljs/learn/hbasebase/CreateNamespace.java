package com.ljs.learn.hbasebase;

import java.io.IOException;

public class CreateNamespace {
    public static void main(String[] args) throws IOException {
        HBaseUtils.createNamespace("testns");

        // 4. 关闭资源
        HBaseUtils.close();
    }
}

