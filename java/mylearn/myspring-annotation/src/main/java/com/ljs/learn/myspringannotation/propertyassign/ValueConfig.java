package com.ljs.learn.myspringannotation.propertyassign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 通过读取配置文件的数据来装配Bean

// 使用 `${}` 之前，需要导入手动配置文件
// 启动后，会读取所有配置文件中的数据
@PropertySource(value={"classpath:/propertyassign/valuetest.properties"})
@Configuration
public class ValueConfig {
    @Bean
    public Person person(){
        return new Person();
    }
}
