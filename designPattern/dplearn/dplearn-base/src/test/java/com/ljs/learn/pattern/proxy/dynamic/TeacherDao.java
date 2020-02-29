package com.ljs.learn.pattern.proxy.dynamic;

public class TeacherDao implements ITeacherDao {
    @Override
    public void teach() {
        System.out.println("is teaching");
    }
}
