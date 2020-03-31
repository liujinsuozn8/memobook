package com.ljs.learn.mybatis.mapperXml.resultmap.simpleMap;

import com.ljs.learn.mybatis.common.bean.User02;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class SimpleMapTest {
    @Test
    public void testResultMap(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper12 mapper = sqlSession.getMapper(UserMapper12.class);
        User02 user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }
}
