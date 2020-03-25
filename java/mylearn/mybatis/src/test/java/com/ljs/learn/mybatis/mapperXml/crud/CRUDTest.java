package com.ljs.learn.mybatis.mapperXml.crud;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import com.ljs.learn.mybatis.mapperXml.crud.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CRUDTest {
    @Test
    public void testGetUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = mapper.getUserById(2);
        System.out.println(user);
    }

    // 添加数据，不commit
    @Test
    public void testInsertUser01(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int result = mapper.insertUser(new User(7, "kkk", "kkkkpw"));

        System.out.println("insertUser = " + result);
        sqlSession.close();
    }

    // 添加数据，然后commit
    @Test
    public void testInsertUser02(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int result = mapper.insertUser(new User(7, "kkk", "kkkk"));

        if (result <= 0){
            System.out.println("insert error");
        } else {
            // 提交事务
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    public void testUpdateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int result = mapper.updateUser(new User(7, "newK", "kkkk"));
        if (result <= 0){
            System.out.println("update error");
        }else {
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    public void testDeleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int result = mapper.deleteUserById(4);
        if (result <= 0){
            System.out.println("update error");
        }else {
            sqlSession.commit();
        }
        sqlSession.close();
    }
}
