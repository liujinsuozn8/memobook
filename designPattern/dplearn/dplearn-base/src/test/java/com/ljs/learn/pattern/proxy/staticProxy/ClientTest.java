package com.ljs.learn.partten.proxy.staticProxy;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建被代理对象
        ITeacherDao teacherDao = new TeacherDao();

        // 创建代理对象，同时将被代理对象作为参数传递给代理对象
        TeacherDaoProxy proxy = new TeacherDaoProxy(teacherDao);

        // 通过代理对象调用被代理对象的方法
        // 即执行的是代理对象的方法，内部执行的是被代理对象的方法
        proxy.teach();
    }
}
