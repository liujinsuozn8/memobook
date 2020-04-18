package com.ljs.learn.mybatis.cache.l1;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserMapper16Test {
    // 连续多次查询相同的数据来测试一级缓存
    @Test
    public void test01(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
        User result01 = mapper.getUserById(1);
        User result02 = mapper.getUserById(1);
        System.out.println("result01 == result02 : " + (result01 == result02));
    }

    // 更新相同数据数据后缓存失效，再次访问会重新检索
    @Test
    public void test02(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
        User result01 = mapper.getUserById(1);

        // 再次查询
        User result02 = mapper.getUserById(1);

        System.out.println("result01 == result02 : " + (result01 == result02));

        // 更新同一条数据
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "newName");
        map.put("pwd", "123456");

        mapper.updateUser(map);

        // 重新检索相同的数据
        User result03 = mapper.getUserById(1);

        System.out.println("result01 == result03 : " + (result01 == result03));
    }

    // 更新不同的数据后缓存失效，再次访问会重新检索
    @Test
    public void test03(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper16 mapper = sqlSession.getMapper(UserMapper16.class);
        User result01 = mapper.getUserById(1);

        // 再次查询
        User result02 = mapper.getUserById(1);

        System.out.println("result01 == result02 : " + (result01 == result02));

        // 更新其他数据
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("name", "newName");
        map.put("pwd", "123456");

        mapper.updateUser(map);

        // 重新检索相同的数据
        User result03 = mapper.getUserById(1);

        System.out.println("result01 == result03 : " + (result01 == result03));
    }

    // 两个mapper共享缓存
    @Test
    public void test04(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper16 mapper01 = sqlSession.getMapper(UserMapper16.class);
        UserMapper16 mapper02 = sqlSession.getMapper(UserMapper16.class);
        System.out.println("mapper01 == mapper02 : " + (mapper01 == mapper02));
        User result01 = mapper01.getUserById(1);
        User result02 = mapper02.getUserById(1);
        System.out.println("result01 == result02 : " + (result01 == result02));
    }
}
