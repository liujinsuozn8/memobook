package com.ljs.learn.myspringannotation.componentScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

// 配置类 == 配置文件

@Configuration // 通过注解声明一个个配置类
// 指定扫描时按照什么规则排除组件
@ComponentScan(value="com.ljs.learn.myspringannotation.componentScan.layers", excludeFilters = {
        // 扫描时排除包含：Controller、Service 注解的类
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
public class ComponentScanConfig {
}
