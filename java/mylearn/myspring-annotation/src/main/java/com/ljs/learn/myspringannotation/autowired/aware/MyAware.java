package com.ljs.learn.myspringannotation.autowired.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class MyAware implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    // 保存注入的IOC容器
    private ApplicationContext applicationContext;

    // ApplicationContextAware 实现
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("IOC = " + applicationContext);
        this.applicationContext = applicationContext;
    }

    // BeanNameAware 实现
    @Override
    public void setBeanName(String name) {
        System.out.println("bean name = " + name);
    }

    // EmbeddedValueResolverAware 实现
    // resolver可以解析字符串中的Spring占位符
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        // 分别获取配置文件中的数据，并执行一个计算
        String result = resolver.resolveStringValue("name = ${name}, compute:11+22= #{11 + 22}");
        System.out.println(result);
    }
}
