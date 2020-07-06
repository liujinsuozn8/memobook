package com.ljs.learn.myspringannotation.regist.componentScan.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        value = "com.ljs.learn.myspringannotation.regist.componentScan.layers",
        includeFilters = {
            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        },
        useDefaultFilters = false
)
public class TypeFilterConfig {
}
