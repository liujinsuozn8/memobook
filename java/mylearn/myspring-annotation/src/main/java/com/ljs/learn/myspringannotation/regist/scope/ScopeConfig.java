package com.ljs.learn.myspringannotation.regist.scope;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(
    value = "com.ljs.learn.myspringannotation.regist.scope"
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

    // 使用懒加载
    @Lazy
    @Bean
    public Student student(){
        System.out.println("create student");
        return new Student("testStudent", 22);
    }
}
