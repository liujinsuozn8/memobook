package com.ljs.learn.myspringannotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {
    @Pointcut("execution(* com.ljs.learn.myspringannotation.aop.MathCalculator.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() + " 开始，参数列表: {"+ Arrays.toString(args) +"}");
    }

    @After(value="pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName() + " 结束");
    }

    // 指定 returning 参数接收函数返回值
    @AfterReturning(value="pointCut()", returning="result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.println(joinPoint.getSignature().getName() + " 正常返回，运行结果: {"+ result +"}");
    }

    // 指定 throwing 参数接收异常
    @AfterThrowing(value="pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println(joinPoint.getSignature().getName() + " 异常，异常信息: " + exception);
    }
}
