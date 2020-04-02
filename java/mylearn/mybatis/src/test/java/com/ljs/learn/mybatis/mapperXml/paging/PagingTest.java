package com.ljs.learn.mybatis.mapperXml.paging;

import com.ljs.learn.mybatis.common.bean.User02;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingTest {
    // 通过sql完成分页
    @Test
    public void testSqlLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper13 mapper = sqlSession.getMapper(UserMapper13.class);
        Map<String, Object> param= new HashMap<>();
        param.put("offset", 2);
        param.put("size", 3);
        List<User02> users = mapper.getUserByLimit(param);
        for (User02 user : users) {
            System.out.println(user);
        }
    }

    // 使用RowsBounds来做分页
    @Test
    public void testRowBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RowBounds rowBounds = new RowBounds(2, 3);
        List<User02> users = sqlSession.selectList("com.ljs.learn.mybatis.mapperXml.paging.UserMapper13.getUserByRowBounds", null, rowBounds);
        for (User02 user : users) {
            System.out.println(user);
        }
    }
}
