package com.ljs.learn.datastructure.stack.usage.calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// 中缀表达式计算器--解决减号问题
public class InfixCalculator2 {
    public static int interpret(String expression) throws InvocationTargetException, IllegalAccessException {
        // 1. 使用单链表实现的栈来创建
        // 创建两个栈: 数栈、符号栈
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        // 2. 通过索引遍历表达式字符串
        char[] expressionChars = expression.toCharArray();
        int rightValue, leftValue, result;
        char c;
        String num = ""; // 多位数时临时保存高位数
        int sign = 1; // 如果是减号，则将sign设置为 -1，设置到下一个数字中，然后将减号转换为加号，并重置为 1

        for (int i = 0; i < expressionChars.length; i++) {
            c = expressionChars[i];
            // 如果是空格则跳过
            if ( CalculatorUtils.isSpace(c) ){ continue; }

            if ( CalculatorUtils.isNum(c) ){
                num += c;
                // 处理多位数
                // 如果 已经到达最后一个字符，则直接入栈，或者下一位不是数值
                if ( i == expressionChars.length - 1 || !CalculatorUtils.isNum(expressionChars[i + 1])){
                    // 保存数值的同时，设置符号
                    numStack.push(Integer.parseInt(num) * sign);
                    // 将符号重置为 1
                    sign = 1;
                    // 清空多位数的缓存
                    num = "";
                }
            } else if ( CalculatorUtils.isOperator(c) ){
                // 如果是符号，需要判断当前符号与栈顶符号的优先级

                // 如果是减号，则将sign置为 -1，并转换为 加号
                if (c == '-'){
                    sign = -1;
                    c = '+';
                }

                // 如果符号栈为空，直接入栈
                if (operatorStack.isEmpty()){
                    operatorStack.push(c);
                } else {
                    // 如果符号栈不为空，需要比较操作符的优先级
                    // 当前符号 <= 栈顶符号
                    if ( CalculatorUtils.compareOperatorLevel(c, operatorStack.peek()) <= 0 ){
                        // 从数栈中pop两个数值
                        // 栈顶元素是右值，下一个元素是左值
                        rightValue = numStack.pop();
                        leftValue = numStack.pop();

                        // 从符号栈pop一个运算符，进行计算
                        result = CalculatorUtils.calculate(operatorStack.pop(), leftValue, rightValue);
                        // 将计算结果放入数栈
                        numStack.push(result);

                        // 将当前符号放入符号栈
                        operatorStack.push(c);
                    } else {
                        // 当前符号 > 栈顶符号
                        // 将当前符号直接放入符号栈
                        operatorStack.push(c);
                    }
                }
            } else {
                throw new RuntimeException("未知字符: " + c);
            }
        }

        // 3. 表达式遍历完后，顺序的从数栈和符号栈中pop出数据进行计算，**直到符号栈为空**
        while ( !operatorStack.isEmpty() ){
            // 从数栈中pop两个数值
            // 栈顶元素是右值，下一个元素是左值
            rightValue = numStack.pop();
            leftValue = numStack.pop();

            // 从符号栈pop一个运算符，进行计算
            result = CalculatorUtils.calculate(operatorStack.pop(), leftValue, rightValue);
            // 将计算结果放入数栈
            numStack.push(result);
        }

        // 4. 最终，符号栈为空，数栈中只有一个元素，即表达式的计算结果
        return numStack.pop();
    }
}
