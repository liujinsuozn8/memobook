package com.ljs.learn.reflect.base;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassLoaderTest {
    @Test
    public void test01() throws ClassNotFoundException {
        //获取系统类加载器
        //输出：sun.misc.Launcher$AppClassLoader@18b4aac2
        ClassLoader classLoader = Test.class.getClassLoader();
        System.out.println(classLoader);

        //获取系统类加载器的父类加载器---扩展类加载器
        //输出：sun.misc.Launcher$ExtClassLoader@6b884d57
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent);

        //无法再通过扩展类加载器来获取父类加载器--引导类加载器
        //输出：null
        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1);

        //测试当前类由那个加载器进行加载
        ClassLoader cls = Class.forName("com.ljs.learn.reflect.base.ClassLoaderTest").getClassLoader();
        System.out.println(cls);

        //Object类的加载器
        //输出：null
        ClassLoader cls2 = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(cls2);
    }

    @Test
    public void test2() throws IOException {
        Properties pros = new Properties();
        //FileInputStream fis = new FileInputStream("jdbc.properties");
        //pros.load(fis);

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        // 创建输入流，该文件必须在src目录下才能读取到
        InputStream is = classLoader.getResourceAsStream("jdbc1.properties");
        System.out.println(is);
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");

        System.out.println(user);
        System.out.println(password);
    }
}
