package com.ljs.learn.myspring.integration.mybatis.mapper;

import com.ljs.learn.myspring.integration.mybatis.bean.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserMapperTest {
    // 通过Mybatis访问数据库
    @Test
    public void testMybatis() throws IOException {
        InputStream in = Resources.getResourceAsStream("integration/mybatis/mybatisConfig/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getAllUsers();
        System.out.println(users);
    }

    // 通过Spring-Mybatis整合Mybatis，并通过Spring的方式访问数据库
    @Test
    public void testSpringMybatis(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("integration/mybatis/springMybatisConfigByXML/applicationContext.xml");
        UserMapperImpl useMapper = (UserMapperImpl) context.getBean("userMapper");
        List<User> users = useMapper.getAllUsers();
        System.out.println(users);
    }

    @Test
    public void testSqlSessionDaoSupport(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("integration/mybatis/springMybatisConfigByXML/applicationContext.xml");
        UserMapperImpl02 useMapper = (UserMapperImpl02) context.getBean("userMapper02");
        List<User> users = useMapper.getAllUsers();
        System.out.println(users);
    }
}
