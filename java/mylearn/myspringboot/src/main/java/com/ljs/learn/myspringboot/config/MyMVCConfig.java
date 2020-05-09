package com.ljs.learn.myspringboot.config;

// 扩展SpringMVC

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

// 定制SpringMVC的方式：
// 实现：WebMvcConfigurer
// 写相关的组件，然后交给SpringBoot
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    // 配置自定义视图解析器
    // 启动后会自动加载到DispatcherServlet的viewResolvers中
    @Bean
    public ViewResolver myViewReslover(){
        return new MyViewReslover();
    }

    // 自定义视图解析器
    public static class MyViewReslover implements ViewResolver{
        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

    // 自定义视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("test");
    }
}
