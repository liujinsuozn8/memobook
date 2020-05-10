package com.ljs.learn.myspringbootshiro.dao;

import com.ljs.learn.myspringbootshiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getUserByName(String name);
}
