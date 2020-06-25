package com.ljs.learn.datastructure.stack.usage;

import com.ljs.learn.datastructure.stack.usage.calculator.InfixCalculator2;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

// 中缀表达式计算器测试
public class InfixCalculator2Test {
    // 基本示例测试
    @Test
    public void testCalculator() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("3 - 1  + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 输出: 6
    }

    // 多位数测试
    @Test
    public void testCalculator02() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("30 - 1  + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 输出: 6
    }

    // 异常字符测试
    @Test
    public void testErrorChar() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("3 - 1 + a + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 产生异常
        // java.lang.RuntimeException: 未知字符: a
    }

    // 单数字测试
    @Test
    public void testOneNumber() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("3");
        System.out.println(result);
        // 输出: 3
    }

    // 减号问题测试
    @Test
    public void testMinus01() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("1 - 5 * 6 + 1");
        // 表达式遍历结束后的栈
        // 1
        // 30 -
        // 1  -

        System.out.println(result);
        // 输出: -28
    }

    @Test
    public void testMinus02() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("9 - 7 * 1 * 1 + 2");
        System.out.println(result);
        // 输出: 4
    }

    @Test
    public void testMinus03() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator2.interpret("9 - 7 + 2");
        System.out.println(result);
        // 输出: 4
    }
}
