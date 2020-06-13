package com.ljs.learn.pattern.interpreter.spring;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpringTest {
    @Test
    public void test01(){
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("10 * 20 * (2+1) -15");
        int result = (Integer)expression.getValue();
        System.out.println(result); // 585
    }
}
