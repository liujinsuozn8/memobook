package com.ljs.learn.myspringannotation.regist.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

// 用于判断系统类型的条件
public class WinCondition implements Condition {
    /**
     *
     * @param context  判断条件可以使用的上下文环境
     * @param metadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1。 获取当前的环境信息
        Environment envObj = context.getEnvironment();

        String envStr = envObj.getProperty("os.name");

        if (envStr.contains("Win")){
            return true;
        } else {
            return false;
        }
    }
}
