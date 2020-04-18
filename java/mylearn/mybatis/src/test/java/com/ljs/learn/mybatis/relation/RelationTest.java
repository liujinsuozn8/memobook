package com.ljs.learn.mybatis.relation;

import com.ljs.learn.mybatis.common.bean02.Student;
import com.ljs.learn.mybatis.common.bean03.Teacher02;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import com.ljs.learn.mybatis.relation.moreToOne.StudentMapper;
import com.ljs.learn.mybatis.relation.oneToMore.Teacher02Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class RelationTest {

    // 多对一映射：自查询方式
    @Test
    public void test01(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // 多对一映射：联表查询方式
    @Test
    public void test02(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudents02();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // 一对多
    @Test
    public void test03(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        Teacher02Mapper mapper = sqlSession.getMapper(Teacher02Mapper.class);
        Teacher02 t = mapper.getTeacherById(1);
        System.out.println(t);
    }

    @Test
    public void test04(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        Teacher02Mapper mapper = sqlSession.getMapper(Teacher02Mapper.class);
        Teacher02 t = mapper.getTeacherById02(1);
        System.out.println(t);
    }
}

