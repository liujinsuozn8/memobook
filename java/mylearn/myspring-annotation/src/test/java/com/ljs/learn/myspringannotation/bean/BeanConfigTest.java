package com.ljs.learn.myspringannotation.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanConfigTest {
    // 获取bean
    @Test
    public void test01(){
        // 1. 创建容器
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        // 2. 根据配置类的方法名，或者Bean中的别名获取bean实例对象
        Person person = (Person) context.getBean("person");
        System.out.println(person);

        Person personAlias = (Person) context.getBean("personAlias");
        System.out.println(personAlias);

        // 3. 通过类型找到IOC容器内部的Bean名
        String[] beanNamesForType = context.getBeanNamesForType(Person.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }
        // 配置类中有 3个 Bean的类型是Person，所以输出：
        // person
        // person01
        // personAlias
    }

}
