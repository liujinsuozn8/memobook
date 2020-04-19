package com.ljs.learn.myspring.integration.mybatis.mapper;

import com.ljs.learn.myspring.integration.mybatis.bean.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl02 extends SqlSessionDaoSupport implements UserMapper{
    @Override
    public List<User> getAllUsers() {
        return getSqlSession().getMapper(UserMapper.class).getAllUsers();
    }
}
