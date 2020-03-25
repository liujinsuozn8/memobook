package com.ljs.learn.mybatis.firstSample.dao;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test01(){
        // 获取资源
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        // 获取mapper
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        // 执行数据库操作
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }

        //关闭资源
        sqlSession.close();
    }
}
