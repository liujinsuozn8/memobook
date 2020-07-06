package com.ljs.learn.myspringannotation.regist.importconfig;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportConfigTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        // 输出
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        // importConfig
        // com.ljs.learn.myspringannotation.regist.importconfig.person.Student
        // com.ljs.learn.myspringannotation.regist.importconfig.person.Teacher
        // com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassA  <<<<ImportSelector接口导入
        // com.ljs.learn.myspringannotation.regist.importconfig.classes.ClassC  <<<<ImportSelector接口导入
        // com.ljs.learn.myspringannotation.regist.importconfig.color.ColorRed
        // com.ljs.learn.myspringannotation.regist.importconfig.color.ColorBlue
        // person
        // rainBow <<<< ImportBeanDefinitionRegistrar接口手动导入
    }

}
