package com.ljs.learn.datastructure.stack.usage.calculator;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

// 后缀表达式计算器
public class SuffixCalculator {
    public static int interpret(String expression) throws InvocationTargetException, IllegalAccessException {
        return calculateSuffixExpr(toSuffixExpression(expression));
    }

    public static int calculateSuffixExpr(List<String> expr) throws InvocationTargetException, IllegalAccessException {
        // 1. 从左向右扫描表达式
        Stack<Integer> numStack = new Stack<>();
        int leftValue,rightValue, result;
        for (String node : expr) {
            if (node.matches("\\d+")){
                // 2. 遇到数字，压入栈
                numStack.push(Integer.parseInt(node));
            } else if (CalculatorUtils.isOperator(node)){
                // 3. 遇到运算符，弹出栈顶的两个数，进行运算。再将结果入栈
                rightValue = numStack.pop();
                leftValue = numStack.pop();
                result = CalculatorUtils.calculate(node, leftValue, rightValue);
                numStack.push(result);
            } else {
                throw new RuntimeException("未知字符: " + node);
            }
        }
        // 4. 重复上述过程直到最右边，栈中只有一个数，即为表达式的结果
        return numStack.pop();
    }

    public static List<String> toSuffixExpression(String expr){
        // 1. 初始化两个栈
        // s1 符号栈
        Stack<String> s1 = new Stack<>();
        // s2 中间结果栈
        List<String> s2 = new LinkedList<>();

        // 2. 从左向右扫描中缀表达式
        char[] exprChars = expr.toCharArray();
        String num = "";
        char c;
        for (int i = 0; i < exprChars.length; i++) {
            c = exprChars[i];
            if ( c == ' '){ continue; }

            if (CalculatorUtils.isNum(c)){
                // 3. 遇到数值时，将其压入 s2
                num += c;
                if (i == exprChars.length - 1 || !CalculatorUtils.isNum(exprChars[i+1])){
                    s2.add(num);
                    num = "";
                }
            } else if (CalculatorUtils.isOperator(c)) {
                // 4. 遇到运算符时
                while (true) {
                    if (s1.isEmpty() || "(".equals(s1.peek())) {
                        // 4.1. 如果 s1 为空，或栈顶运算符为 `(`，则压入 s1
                        s1.push(String.valueOf(c));
                        break;
                    } else {
                        // 4.2. 如果 s1 不为空，与 s1 栈顶比较优先级
                        if (CalculatorUtils.compareOperatorLevel(c, s1.peek()) > 0) {
                            // 4.2.1 `当前运算符 > s1 栈顶` 时，将当前运算符压入 s1
                            s1.push(String.valueOf(c));
                            break;
                        } else {
                            // 4.2.2 `当前运算符 <= s1 栈顶` 时，
                            // 4.2.2.1 将 s1 栈顶的运算符弹出，压入 s2
                            s2.add(s1.pop());
                            // 4.2.2.2 跳转到 4-1，继续检查当前运算符
                        }
                    }
                }
            } else if (CalculatorUtils.isBrackets(c)){
                // 5. 遇到括号时
                // 5.1 如果是 `(`，压入 s1
                if (c == '('){
                    s1.push(String.valueOf(c));
                } else {
                    // 5.2 如果是 `)`，依次弹出 s1 栈顶，并压入 s2，直到s1弹出的是 `(`
                    String operator;
                    while ( !"(".equals(operator = s1.pop()) ){
                        s2.add(operator);
                    }
                }
            } else {
                throw new RuntimeException("未知字符: " + c);
            }
        }
        // 6. 重复 2至5，直到表达式结束

        // 7. 将 s1 中剩余的运算符依次弹出，并压入 s2
        while (!s1.isEmpty()){
            s2.add(s1.pop());
        }

        // 8. 将中间结果栈作为结果返回
        return s2;
    }
}



