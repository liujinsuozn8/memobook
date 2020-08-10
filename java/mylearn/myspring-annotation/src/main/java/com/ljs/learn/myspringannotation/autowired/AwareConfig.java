package com.ljs.learn.myspringannotation.autowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// Aware 接口测试
// 扫描包，并添加配置文件
@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.autowired.aware")
@PropertySource("classpath:/autowired/application.properties")
public class AwareConfig {
}
