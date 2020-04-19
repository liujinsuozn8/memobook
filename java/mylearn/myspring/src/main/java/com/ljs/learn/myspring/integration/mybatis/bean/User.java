package com.ljs.learn.myspring.integration.mybatis.bean;

// -- 创建表
// create table user (
// `id` int(20) not null primary key,
// `name` varchar(30) default null,
// `pwd` varchar(30) default null
// )default charset=utf8
//
// -- 添加数据
// insert into user
// values
// (1, "aaa", "aaapwd"),
// (2, "bbb", "bbbpwd"),
// (3, "ccc", "cccpwd"),
// (4, "ddd", "dddpwd"),
// (5, "eee", "eeepwd"),
// (6, "fff", "fffpwd")

public class User {
    private int id;
    private String name;
    private String pwd;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
