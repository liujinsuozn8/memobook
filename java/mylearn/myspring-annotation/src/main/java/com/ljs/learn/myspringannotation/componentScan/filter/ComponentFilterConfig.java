package com.ljs.learn.myspringannotation.componentScan.filter;

import com.ljs.learn.myspringannotation.componentScan.layers.service.DemoService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(
    value="com.ljs.learn.myspringannotation.componentScan.layers",
    includeFilters = {
        @ComponentScan.Filter(type=FilterType.ANNOTATION, classes={Repository.class}),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes={DemoService.class}),
    },
    useDefaultFilters = false
)
public class ComponentFilterConfig {

}
