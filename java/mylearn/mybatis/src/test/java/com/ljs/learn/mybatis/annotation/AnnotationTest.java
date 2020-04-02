package com.ljs.learn.mybatis.annotation;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class AnnotationTest {
    @Test
    public void testAnnotation(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper14 mapper = sqlSession.getMapper(UserMapper14.class);
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testGetUserByID(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper14 mapper = sqlSession.getMapper(UserMapper14.class);
        User user = mapper.getUserByID(3);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testInsertUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper14 mapper = sqlSession.getMapper(UserMapper14.class);
        int result = mapper.insertUser(new User(10, "asdf", "xcvbvc"));

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
        UserMapper14 mapper = sqlSession.getMapper(UserMapper14.class);
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
        UserMapper14 mapper = sqlSession.getMapper(UserMapper14.class);
        int result = mapper.deleteUserById(4);
        if (result <= 0){
            System.out.println("update error");
        }else {
            sqlSession.commit();
        }
        sqlSession.close();
    }
}
