package com.ljs.learn.myspringannotation.autowired;

import com.ljs.learn.myspringannotation.autowired.profile.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.SQLOutput;

// @Profile测试
public class ProfileConfig {
    @Profile("dev")
    @Bean
    public Env devEnv(){
        System.out.println("create dev env");
        return new DevEnv();
    }

    @Profile("test")
    @Bean
    public Env testEnv(){
        return new TestEnv();
    }

    @Profile("product")
    @Bean
    public Env productEnv(){
        return new ProductEnv();
    }

    @Profile("default")
    @Bean
    public Env defaultEnv(){
        return new DefaultEnv();
    }

    @Bean
    public Env otherEnv(){
        return new OtherEnv();
    }
}
