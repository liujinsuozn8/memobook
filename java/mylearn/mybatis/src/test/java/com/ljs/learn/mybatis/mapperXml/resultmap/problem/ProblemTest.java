package com.ljs.learn.mybatis.mapperXml.resultmap.problem;

import com.ljs.learn.mybatis.common.bean.User02;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ProblemTest {
    @Test
    public void problemTest(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper10 mapper = sqlSession.getMapper(UserMapper10.class);
        User02 user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }
}
