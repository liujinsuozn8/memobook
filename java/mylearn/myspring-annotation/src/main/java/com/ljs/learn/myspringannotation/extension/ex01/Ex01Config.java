package com.ljs.learn.myspringannotation.extension.ex01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.extension.ex01")
public class Ex01Config {
    @Bean
    public Object obj01(){
        System.out.println("obj01 init");
        return new Object();
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Ex01Config.class);
        context.close();
    }
}
