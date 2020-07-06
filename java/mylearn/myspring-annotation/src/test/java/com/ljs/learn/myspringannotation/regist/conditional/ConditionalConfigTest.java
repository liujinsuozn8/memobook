package com.ljs.learn.myspringannotation.regist.conditional;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class ConditionalConfigTest {
    @Test
    public void testConditionalConfig(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        ConfigurableEnvironment environment = context.getEnvironment();

        // 检查输入
        String envStr = environment.getProperty("os.name");
        System.out.println(envStr);

        // 输出 类型是 Person的类
        String[] names = context.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }
}
