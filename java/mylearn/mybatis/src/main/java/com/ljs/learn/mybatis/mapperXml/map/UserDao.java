package com.ljs.learn.mybatis.mapperXml.map;

import com.ljs.learn.mybatis.common.bean.User;

import java.util.Map;

public interface UserDao {
    // 通过map对象来执行插入
    int insertUser(Map<String, Object> map);
}
