package com.ljs.learn.datastructure.stack.usage;

import com.ljs.learn.datastructure.stack.usage.calculator.SuffixCalculator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

// 后缀表达式计算器测试
public class SuffixCalculatorTest {
    // 基本测试
    @Test
    public void testToSuffixExpression(){
        List<String> list = SuffixCalculator.toSuffixExpression("1 + ((2 + 3) * 4) - 5");
        String[] result = {"1", "2", "3", "+", "4", "*", "+", "5", "-"};

        assert (list.toString().equals(Arrays.toString(result)));
    }

    // 多数字测试
    @Test
    public void testToSuffixExpressionMultiNum(){
        List<String> list = SuffixCalculator.toSuffixExpression("10 + ((21 + 3) * 4) - 5");
        String[] result = {"10", "21", "3", "+", "4", "*", "+", "5", "-"};

        assert (list.toString().equals(Arrays.toString(result)));
    }

    // 计算测试
    @Test
    public void testInterpret() throws InvocationTargetException, IllegalAccessException {
        int result = SuffixCalculator.interpret("1 + ((2 + 3) * 4) - 5");
        assert (result == 16);
    }

    // 多数字计算测试
    @Test
    public void testInterpretMultiNum() throws InvocationTargetException, IllegalAccessException {
        int result = SuffixCalculator.interpret("10 + ((21 + 3) * 4) - 5");
        assert (result == 101);
    }
}
