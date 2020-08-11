package com.ljs.learn.myspringannotation.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class AopConfigTest {
    // 切面测试
    @Test
    public void calculatorTest(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator calculator = context.getBean(MathCalculator.class);
        int result = calculator.div(6, 3);
        System.out.println(result);
        // div 开始，参数列表: {[6, 3]}
        // div 结束
        // div 正常返回，运行结果: {2}
        // 2

        result = calculator.div(6, 0);
        // div 开始，参数列表: {[6, 0]}
        // div 结束
        // div 异常，异常信息: java.lang.ArithmeticException: / by zero
    }
}