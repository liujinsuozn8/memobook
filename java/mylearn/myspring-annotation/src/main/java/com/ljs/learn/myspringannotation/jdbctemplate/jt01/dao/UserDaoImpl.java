package com.ljs.learn.myspringannotation.jdbctemplate.jt01.dao;

import com.ljs.learn.myspringannotation.jdbctemplate.jt01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        String sql = "insert into t_user values (?, ?, ?)";
        int insertCount = jdbcTemplate.update(sql, user.getUserId(), user.getUsername(), user.getUstatus());
        System.out.println("insert data count = " + insertCount);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update t_user set username=?, ustatus=? where user_id=?";
        int updateCount = jdbcTemplate.update(sql, user.getUsername(), user.getUstatus(), user.getUserId());
        System.out.println("update data count = " + updateCount);
    }

    @Override
    public void deleteUser(String userId) {
        String sql = "delete from t_user where user_id=?";
        int deleteCount = jdbcTemplate.update(sql, userId);
        System.out.println("delete data count = " + deleteCount);
    }

    @Override
    public int selectCount() {
        String sql = "select count(*) from t_user";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    @Override
    public User findUser(String userId) {
        String sql = "select * from t_user where user_id=?";

        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userId);
        return user;
    }

    @Override
    public List<User> findAllUser() {
        String sql = "select * from t_user";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        String sql = "insert into t_user values (?, ?, ?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdateUser(List<Object[]> batchArgs) {
        String sql = "update t_user set username=?, ustatus=? where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDeleteUser(List<Object[]> batchArgs) {
        String sql = "delete from t_user where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }

}
