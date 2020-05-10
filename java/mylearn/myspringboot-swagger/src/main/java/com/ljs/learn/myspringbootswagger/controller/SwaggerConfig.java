package com.ljs.learn.myspringbootswagger.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //启动swagger
public class SwaggerConfig {
    // 配置Swagger的Docket的实例
    @Bean
    public Docket docket(Environment env){
        // 设置配置中哪些变量有效：只在测试和开发环境启动
        Profiles profiles = Profiles.of("dev", "prot");
        // 检查配置中是否包含指定的配置
        boolean flg = env.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flg)
                .apiInfo(getApiInfo())
                .groupName("my docket")
                // 根据配置来设置是否启动swagger
                .select()
                // 配置扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.ljs.learn.myspringbootswagger.controller"))
                // 过滤路径
                // .paths(PathSelectors.)
                .build();
    }

    // 配置 api document部分的信息
    private ApiInfo getApiInfo(){
        return new ApiInfo(
            "ljs API Documentation",
            "ljs Api Documentation",
            "1.0",
            "urn:tos",
            new Contact("LJS", "XXXX","1111@com"), // 作者信息
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList()
        );
    }

    // 配置多个分组
    @Bean
    public Docket docket01(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("docket01");
    }

    @Bean
    public Docket docket02(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("docket02");
    }

    @Bean
    public Docket docket03(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("docket03");
    }
}
