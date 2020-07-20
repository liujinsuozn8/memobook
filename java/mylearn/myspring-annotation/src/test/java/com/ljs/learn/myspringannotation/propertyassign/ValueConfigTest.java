package com.ljs.learn.myspringannotation.propertyassign;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.Assert.*;

public class ValueConfigTest {
    // 测试获取 bean
    @Test
    public void person() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueConfig.class);
        Person person = (Person) context.getBean("person");
        System.out.println(person);

        Person test = new Person();
        test.setName("testName");
        test.setAge(20);
        test.setAddress("testAddress");

        assertEquals(person, test);
    }

    // 测试手动获取配置文件中的数据
    @Test
    public void getPropertiesValue(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValueConfig.class);
        // 1. 获取环境变量对象
        ConfigurableEnvironment env = context.getEnvironment();

        // 2. 通过环境变量对象获取配置文件中的数据
        String address = env.getProperty("address");

        assertEquals(address, "testAddress");
    }
}