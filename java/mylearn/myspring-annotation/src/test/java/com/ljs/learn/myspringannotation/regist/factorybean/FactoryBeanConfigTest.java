package com.ljs.learn.myspringannotation.regist.factorybean;

import com.ljs.learn.myspringannotation.regist.factorybean.color.Color;
import com.ljs.learn.myspringannotation.regist.factorybean.color.ColorFactoryBean;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class FactoryBeanConfigTest {

    // 测试通过工厂类获取bean
    @Test
    public void colorFactoryBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
        // 输出:
        // factoryBeanConfig
        // colorFactoryBean
        // 不会输出color

        // 通过 colorFactoryBean 获取实例时，返回的是Color的实例对象
        Object color1 = context.getBean("colorFactoryBean");
        assert color1.getClass() == Color.class;

        // 测试单实例设置
        Object color2 = context.getBean("colorFactoryBean");
        assert color1 == color2;
    }

    // 测试获取工厂bean自己
    public void testGetFactoryBeanSelf(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ColorFactoryBean.class);

        // 通过 & 获取工厂bean自身
        Object bean = context.getBean("&colorFactoryBean");

        assert bean.getClass() == ColorFactoryBean.class;
    }
}