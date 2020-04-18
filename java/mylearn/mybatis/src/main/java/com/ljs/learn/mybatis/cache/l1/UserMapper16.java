package com.ljs.learn.mybatis.cache.l1;

import com.ljs.learn.mybatis.common.bean.User;

import java.util.Map;

public interface UserMapper16 {
    User getUserById(int id);
    int updateUser(Map map);
}
