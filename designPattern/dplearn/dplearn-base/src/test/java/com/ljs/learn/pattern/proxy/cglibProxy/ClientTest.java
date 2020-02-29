package com.ljs.learn.pattern.proxy.cglibProxy;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建目标对象
        TeacherDao target = new TeacherDao();

        // 创建cglib代理对象
        TeacherDao proxyInstance = new ProxyFactory<TeacherDao>(target).getProxyInstance();
        proxyInstance.teach();
    }
}
