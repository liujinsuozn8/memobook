package com.ljs.learn.reflect.structure;

import org.junit.Test;

public class PackageTest {
    //获取运行时类所在的包
    @Test
    public void test01(){
        Class clazz = Person.class;
        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);

        // output:
        // package com.ljs.learn.reflect.structure
    }
}
