package com.ljs.learn.datastructure.stack.usage.calculator;

import java.lang.reflect.InvocationTargetException;

// 计算器辅助工具
public class CalculatorUtils {
    // 检查当前字符是否为 运算符
    public static boolean isOperator(char o){
        return Operator.isOperator(o);
    }
    public static boolean isOperator(String o){
        return isOperator(o.charAt(0));
    }
    // 检查当前字符是否为 数字
    public static boolean isNum(char c){
        return '0' <= c && c <= '9';
    }
    public static boolean isNum(String c){
        return isNum(c.charAt(0));
    }

    // 判断是否为括号 (、）
    public static boolean isBrackets(char c){
        return c == '(' || c == ')';
    }
    public static boolean isBrackets(String c){
        return isBrackets(c.charAt(0));
    }

    // 判断是否为空格
    public static boolean isSpace(char c){
        return c == ' ';
    }


    // 获取运算符的优先级
    public static int getOperatorLevel(char o){
        return Operator.getLevel(o);
    }
    public static int getOperatorLevel(String o){
        return getOperatorLevel(o.charAt(0));
    }

    // 执行计算
    public static int calculate(char o, int left, int right) throws InvocationTargetException, IllegalAccessException {
        return Operator.calculate(o, left, right);
    }
    public static int calculate(String o, int left, int right) throws InvocationTargetException, IllegalAccessException {
        return calculate(o.charAt(0), left, right);
    }

    // 比较两个操作符的优先级; < -1; = 0; > 1
    public static int compareOperatorLevel(char a, char b){
        int aLevel = Operator.getLevel(a);
        int bLevel = Operator.getLevel(b);

        if (aLevel < bLevel){
            return -1;
        } else if ( aLevel == bLevel ){
            return 0;
        } else {
            return 1;
        }
    }
    public static int compareOperatorLevel(String a, String b){
        return compareOperatorLevel(a.charAt(0), b.charAt(0));
    }
    public static int compareOperatorLevel(String a, char b){
        return compareOperatorLevel(a.charAt(0), b);
    }
    public static int compareOperatorLevel(char a, String b){
        return compareOperatorLevel(a, b.charAt(0));
    }
}
