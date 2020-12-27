package com.ljs.learn.javasample;

import java.io.FileWriter;
import java.io.IOException;

public class JavaJobSample {
    // 向文件末尾添加字符串
    public static void main(String[] args) {
        FileWriter f = null;

        try {
            f = new FileWriter("/opt/module/java_output.txt", true);
            f.write("azkaban java job test\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f != null){
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
