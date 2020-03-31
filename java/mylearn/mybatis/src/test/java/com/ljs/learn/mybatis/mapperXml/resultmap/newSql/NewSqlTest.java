package com.ljs.learn.mybatis.mapperXml.resultmap.newSql;

import com.ljs.learn.mybatis.common.bean.User02;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class NewSqlTest {
    @Test
    public void testNewSql(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper11 mapper = sqlSession.getMapper(UserMapper11.class);
        User02 user = mapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }
}
