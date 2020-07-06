package com.ljs.learn.myspringannotation.regist.factorybean;

import com.ljs.learn.myspringannotation.regist.factorybean.color.ColorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanConfig {
    /** 注册到容器中
     * 虽然注册到容器的是 `ColorFactoryBean`，但是通过 `colorFactoryBean` 获取对象时，
     * 会调用 `getObject()` 方，返回的 Color 对象
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
