package com.ljs.learn.mybatis.common.bean02;

public class Student {
    private int id;
    private String name;

    // 产生关联关系时，不直接参照数据库表来设置字段
    // 而已直接添加类之间的依赖关系
    // private int tid;
    private Teacher teacher;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}

