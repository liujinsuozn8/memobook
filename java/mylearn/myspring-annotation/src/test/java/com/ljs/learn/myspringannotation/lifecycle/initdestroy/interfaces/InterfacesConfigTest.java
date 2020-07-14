package com.ljs.learn.myspringannotation.lifecycle.initdestroy.interfaces;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class InterfacesConfigTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InterfacesConfig.class);
        System.out.println("容器创建完成");
        System.out.println("关闭容器");
        context.close();

        // 输出
        // car afterPropertiesSet
        // computer afterPropertiesSet
        // computer init
        // 容器创建完成
        // 关闭容器
        // computer destroy
        // computer end
        // car DisposableBean destroy
    }

    @Test
    public void prototypeComputer(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InterfacesConfig.class);
        System.out.println("容器创建完成");
        Computer computer01 = (Computer) context.getBean("prototypeComputer");
        Computer computer02 = (Computer) context.getBean("prototypeComputer");
        // 输出
        // car afterPropertiesSet       <<<< 单例对象的输出
        // computer afterPropertiesSet  <<<< 单例对象的输出
        // computer init                <<<< 单例对象的输出
        // 容器创建完成
        // computer afterPropertiesSet
        // computer init
        // computer afterPropertiesSet
        // computer init
    }


}