package com.ljs.learn.myspring.integration.mybatis.mapper;

import com.ljs.learn.myspring.integration.mybatis.bean.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{
    // 通过spring注入 SqlSessionTemplate，来获取Mybatis 生成的mapper对象
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        return sqlSessionTemplate.getMapper(UserMapper.class).getAllUsers();
    }
}
