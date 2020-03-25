package com.ljs.learn.mybatis.mapperXml.map;

import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;

public class MapParamTest {
    @Test
    public void testInsertUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        // 创建Map对象
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", 8);
        map.put("username", "tttt");
        map.put("userpwd", "ttttpwd");

        int result = mapper.insertUser(map);

        if (result <= 0){
            System.out.println("insert error");
        }else {
            sqlSession.commit();
        }
        sqlSession.close();
    }
}
