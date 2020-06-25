package com.ljs.learn.datastructure.stack.usage;

import com.ljs.learn.datastructure.stack.usage.calculator.CalculatorUtils;
import com.ljs.learn.datastructure.stack.usage.calculator.InfixCalculator;
import com.ljs.learn.datastructure.stack.usage.calculator.Operator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

// 中缀表达式计算器测试
public class InfixCalculatorTest {
    // 基本示例测试
    @Test
    public void testCalculator() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("3 - 1  + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 输出: 6
    }

    // 多位数测试
    @Test
    public void testCalculator02() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("30 - 1  + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 输出: 6
    }

    // 异常字符测试
    @Test
    public void testErrorChar() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("3 - 1 + a + 2 * 3 - 4 / 2");
        System.out.println(result);
        // 产生异常
        // java.lang.RuntimeException: 未知字符: a
    }

    // 单数字测试
    @Test
    public void testOneNumber() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("3");
        System.out.println(result);
        // 输出: 3
    }

    // 减号问题测试
    @Test
    public void testMinus01() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("1 - 5 * 6 + 1");
        // 表达式遍历结束后的栈
        // 1
        // 30 +
        // 1  -

        System.out.println(result);
        // 结果为-28，但是输出 -30
    }

    @Test
    public void testMinus02() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("9 - 7 * 1 + 2");
        System.out.println(result);
        // 结果为4，但是输出 0
    }

    @Test
    public void testMinus03() throws InvocationTargetException, IllegalAccessException {
        int result = InfixCalculator.interpret("9 - 7 + 2");
        System.out.println(result);
        // 输出: 4
    }

    // @Test
    // public void test() throws InvocationTargetException, IllegalAccessException {
        // CalculatorUtils.calculate('-', 2,3);
        // Operator.calculate('-', 2,3);
        // Operator.operatorMethod.get('-').invoke(null, (Object) new int[]{2, 3});
        // Operator.operatorMethod.get('-').invoke(null, 2, 3);
    // }
}
