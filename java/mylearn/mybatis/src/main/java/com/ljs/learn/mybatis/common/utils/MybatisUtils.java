package com.ljs.learn.mybatis.common.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class  MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    // 获取sqlSessionFactory对象
    static{
        InputStream inputStream = null;
        try {
            String resource = "mybatis-config.xml";
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 返回执行sql的对象
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
