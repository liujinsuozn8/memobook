package com.ljs.learn.mybatis.relation.moreToOne;

import com.ljs.learn.mybatis.common.bean02.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> getStudents();
    List<Student> getStudents02();
}
