package com.ljs.learn.collection.map;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    @Test
    public void test() throws IOException {
        // 创建 Properties对象
        Properties properties = new Properties();
        // 获取文件的输入流
        FileInputStream is = new FileInputStream("jdbc.properties");

        // 读取文件输入流，并从文件中解析属性
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        System.out.println("user = " + user);
        System.out.println("password = " + password);
    }
}
