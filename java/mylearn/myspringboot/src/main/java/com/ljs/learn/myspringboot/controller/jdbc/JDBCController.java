package com.ljs.learn.myspringboot.controller.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 查询
    @RequestMapping("/jdbc/userlist")
    public List<Map<String, Object>> userList(){
        String sql = "select * from test01.user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    // 添加
    @RequestMapping("/jdbc/adduser")
    public String addUser(){
        String sql = "insert into test01.user(id, name, pwd) values(100, 'xxxx', 'xxxpwd')";
        // 自动提交事务
        jdbcTemplate.update(sql);
        return "add end";
    }

    // 修改
    @RequestMapping("/jdbc/upuser/{id}")
    public String updateUser(@PathVariable("id") int id){
        String sql = "update test01.user set name=?, pwd=? where id="+id;

        // 封装对象
        Object[] objects = new Object[2];
        objects[0] = "yyy";
        objects[1] = "yyypwd";
        jdbcTemplate.update(sql, objects);
        return "update end";
    }
    // 删除
    @RequestMapping("/jdbc/deluser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        String sql = "delete from test01.user where id = ?";
        jdbcTemplate.update(sql, id);
        return "delete end";
    }
}
