package com.ljs.learn.mybatis.common.bean03;

import java.util.List;

public class Teacher02 {
    private int id;
    private String name;

    // 配置一对多关系：一个老师对应多个学生
    private List<Student02> students;

    @Override
    public String toString() {
        return "Teacher02{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
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

    public List<Student02> getStudents() {
        return students;
    }

    public void setStudents(List<Student02> students) {
        this.students = students;
    }
}
