package com.ljs.learn.myspringannotation.regist.importconfig;

import com.ljs.learn.myspringannotation.regist.importconfig.color.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 对所有需要添加到容器中的bean，调用
     *  BeanDefinitionRegistry.registerBeanDefinition 手动注册
     * @param importingClassMetadata 当前类的注解信息
     * @param registry BeanDefinition 的注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 根据容器的已有的bean来添加其他的bean
        // 1. 判断容器中是否有红色和蓝色
        boolean hasRed = registry.containsBeanDefinition("com.ljs.learn.myspringannotation.regist.importconfig.color.ColorRed");
        boolean hasBlue = registry.containsBeanDefinition("com.ljs.learn.myspringannotation.regist.importconfig.color.ColorBlue");

        // 2. 如果容器中有红色和蓝色，则添加 RainBow
        if (hasRed && hasBlue){
            // 指定bean的定义信息
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            // 指定bean名，注册bean
            registry.registerBeanDefinition("rainBow", beanDefinition);
        }
    }
}
