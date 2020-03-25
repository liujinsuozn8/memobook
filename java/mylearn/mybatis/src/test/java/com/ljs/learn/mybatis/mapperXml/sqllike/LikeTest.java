package com.ljs.learn.mybatis.mapperXml.sqllike;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class LikeTest {
    // 模糊查询，将通配符写在配置文件中
    @Test
    public void test01(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUser01("a");

        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    // 模糊查询，需要将通配符写在参数中
    @Test
    public void test02(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUser02("%a%");
        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }
}
