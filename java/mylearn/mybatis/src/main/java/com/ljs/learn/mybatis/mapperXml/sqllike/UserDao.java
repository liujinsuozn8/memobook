package com.ljs.learn.mybatis.mapperXml.sqllike;

import com.ljs.learn.mybatis.common.bean.User;

import java.util.List;

public interface UserDao {
    // 模糊查询，将通配符写在配置文件中
    List<User> getUser01(String name);

    // 模糊查询，需要将通配符写在参数中
    List<User> getUser02(String name);
}
