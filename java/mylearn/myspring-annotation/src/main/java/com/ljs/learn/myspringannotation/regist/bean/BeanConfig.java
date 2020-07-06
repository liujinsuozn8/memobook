package com.ljs.learn.myspringannotation.regist.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置类 == 配置文件
@Configuration // 通过注解声明一个个配置类
public class BeanConfig {
    // 向容器中注册一个Bean
    // Bean的类型 = 返回值的类型
    // Bean的id = 方法名
    @Bean
    public Person person(){
        return new Person("personA", 22);
    }

    @Bean
    public Person person01(){
        return new Person("personB", 23);
    }

    // 在Bean注解中，手动指定Bean的别名
    @Bean("personAlias")
    public Person person02(){
        return new Person("personC", 24);
    }

}
