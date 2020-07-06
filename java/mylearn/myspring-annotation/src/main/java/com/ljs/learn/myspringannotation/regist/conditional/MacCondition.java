package com.ljs.learn.myspringannotation.regist.conditional;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

// 用于判断系统类型的条件
public class MacCondition implements Condition {
    /**
     *
     * @param context  判断条件可以使用的上下文环境
     * @param metadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1. 获取ioc容器中使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // 2. 获取类加载器
        ClassLoader classLoader = context.getClassLoader();

        // 3。 获取当前的环境信息
        Environment envObj = context.getEnvironment();

        // 4. 获取bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        String envStr = envObj.getProperty("os.name");

        if (envStr.contains("Mac")){
            return true;
        } else {
            return false;
        }
    }
}
