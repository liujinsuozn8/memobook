package com.ljs.learn.base.javaconfig.config;

import com.ljs.learn.base.javaconfig.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 主配置类
@Configuration
@Import(SubConfig.class) //引入其他配置类
public class MyConfig {
    @Bean
    public User user(){
        return new User();
    }
}
