package com.ljs.learn.myspringannotation.jdbctemplate.jt01.entity;

// create table t_user(
// `user_id` bigint(20) primary key not null,
// `username` varchar(100) not null,
// `ustatus` varchar(50) not null
// )

public class User {
    private String userId;

    private String username;

    private String ustatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", ustatus='" + ustatus + '\'' +
                '}';
    }
}
