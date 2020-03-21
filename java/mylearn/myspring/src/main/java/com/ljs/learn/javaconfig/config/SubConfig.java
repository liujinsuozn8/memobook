package com.ljs.learn.javaconfig.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 子配置类
@Configuration
@ComponentScan("com.ljs.learn.javaconfig.bean")
public class SubConfig {
    // 不声明任何bean，通过@ComponentScan来扫描指定包下被@Component装饰的类
}
