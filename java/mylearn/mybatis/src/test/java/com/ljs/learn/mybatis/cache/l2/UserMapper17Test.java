package com.ljs.learn.mybatis.cache.l2;

import com.ljs.learn.mybatis.common.bean.User;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class UserMapper17Test {

    // 同时打开两个SqlSession对象，同时操作一个mapper，并且同时启动、同时关闭
    @Test
    public void test01(){
        // 1. 同时打开两个SqlSession对象，同时操作一个mapper接口，
        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);

        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);

        // 2. 两个SqlSession分别进行检索
        User result01 = mapper1701.getUserById(1);
        User result02 = mapper1702.getUserById(1);

        System.out.println("result01 == result02 : " + (result01 == result02));

        // 3.同时关闭
        sqlSession01.close();
        sqlSession02.close();
    }

    // 创建第一个sqlSession并执行检索，然后关闭，一级缓存的数据保存到二级缓存。再创建第二个sqlSession并执行检索
    @Test
    public void test02(){
        // 1. 创建第一个sqlSession并执行检索，然后关闭
        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);

        User result01 = mapper1701.getUserById(1);
        sqlSession01.close();

        // 2. 再创建第二个sqlSession并执行检索
        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);

        User result02 = mapper1702.getUserById(1);
        sqlSession02.close();

        System.out.println("result01 == result02 : " + (result01 == result02));
    }

    // 测试缓存属性时间：6000毫秒
    @Test
    public void test03(){
        // 1. 创建第一个sqlSession并执行检索，然后关闭
        SqlSession sqlSession01 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1701 = sqlSession01.getMapper(UserMapper17.class);

        User result01 = mapper1701.getUserById(1);
        sqlSession01.close();

        // 2. 再创建第二个sqlSession并执行检索
        SqlSession sqlSession02 = MybatisUtils.getSqlSession();
        UserMapper17 mapper1702 = sqlSession02.getMapper(UserMapper17.class);

        // 3. 暂停
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User result02 = mapper1702.getUserById(1);
        sqlSession02.close();

        System.out.println("result01 == result02 : " + (result01 == result02));
    }
}
