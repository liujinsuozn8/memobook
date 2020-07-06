package com.ljs.learn.myspringannotation.regist.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

// 用于判断系统类型的条件
public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment envObj = context.getEnvironment();
        String envStr = envObj.getProperty("os.name");

        if (envStr.contains("Linux")){
            return true;
        } else {
            return false;
        }
    }
}
