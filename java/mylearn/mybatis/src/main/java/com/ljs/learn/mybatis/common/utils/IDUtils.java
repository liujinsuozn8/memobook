package com.ljs.learn.mybatis.common.utils;

import java.util.UUID;

// 生成随机UUID
public class IDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
