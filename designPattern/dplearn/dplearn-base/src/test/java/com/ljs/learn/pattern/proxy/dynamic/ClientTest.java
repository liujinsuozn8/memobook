package com.ljs.learn.pattern.proxy.dynamic;

import org.junit.Test;

public class ClientTest {
    @Test
    public void test01(){
        // 创建目标对象
        ITeacherDao target = new TeacherDao();
        
        // 通过com.sun.proxy.$Proxy4在内存中动态生成代理对象
        ITeacherDao proxyInstance = new ProxyFactory<ITeacherDao>(target).getProxyInstance();

        System.out.println(proxyInstance);
        System.out.println(proxyInstance.getClass());

        // 通过代理对象，调用目标对象的方法
        proxyInstance.teach();
    }
}
