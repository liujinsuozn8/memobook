package com.ljs.learn.mybatis.configXml.mappers;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import com.ljs.learn.mybatis.configXml.typeAlias.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MappersTest {
    @Test
    public void testMappers(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = mapper.getUserById(2);
        System.out.println(user);

        sqlSession.close();
    }
}
