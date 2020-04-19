package com.ljs.learn.myspring.integration.mybatis.transaction;

import com.ljs.learn.myspring.integration.mybatis.bean.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperImpl implements UserMapper {
    // 通过spring注入 SqlSessionTemplate，来获取Mybatis 生成的mapper对象
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        return sqlSessionTemplate.getMapper(UserMapper.class).getAllUsers();
    }

    @Override
    public int addUser(Map map) {
        return sqlSessionTemplate.getMapper(UserMapper.class).addUser(map);
    }

    @Override
    public int deleteUser(int id) {
        return sqlSessionTemplate.getMapper(UserMapper.class).deleteUser(id);
    }

    public void transcation(){
        // 先插入在删除，删除时会有异常，通过数据的插入状况来检查事务
        Map<String, Object> map = new HashMap<>();
        map.put("id", 100);
        map.put("name", "xxx");
        map.put("pwd", "xcvb");
        addUser(map);

        deleteUser(10);
    }
}
