package com.ljs.learn.mybatis.annotation;

import com.ljs.learn.mybatis.common.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper14 {
    // 获取所有用户
    @Select("select * from user")
    List<User> getUsers();

    // 通过Id检索用户
    @Select("select * from user where id = #{id}")
    User getUserByID(@Param("id") int id);

    // 插入用户
    @Insert("insert into test01.user (id, name, pwd) values (#{id}, #{name}, #{pwd});")
    int insertUser(User user);

    // 更新用户
    @Update("update test01.user set name=#{name} where id = #{id}")
    int updateUser(User user);

    // 删除用户
    @Delete("delete from test01.user where id = #{id}")
    int deleteUserById(int id);
}
