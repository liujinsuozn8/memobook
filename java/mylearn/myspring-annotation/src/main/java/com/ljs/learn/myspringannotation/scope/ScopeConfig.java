package com.ljs.learn.myspringannotation.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(
    value = "com.ljs.learn.myspringannotation.scope"
)
public class ScopeConfig {
    // 测试 prototype 的调用次数
    private int prototype_count = 0;

    // 默认使用单例
    @Bean
    public Person person(){
        System.out.println("singleton person create");
        return new Person("personA", 22);
    }

    // 使用多实例
    @Scope("prototype")
    @Bean
    public Person person02(){

        System.out.println("prototype person create: " + ++prototype_count);
        return new Person("personB", 33);
    }
}
