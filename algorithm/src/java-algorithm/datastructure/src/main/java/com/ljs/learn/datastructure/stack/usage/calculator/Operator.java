package com.ljs.learn.datastructure.stack.usage.calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Operator {
    private static Map<Character, Integer> operatorLevel;
    public static Map<Character, Method> operatorMethod;

    static {
        // 保存运算符的等级
        operatorLevel = new HashMap<>();
        operatorLevel.put('+', 1);
        operatorLevel.put('-', 1);
        operatorLevel.put('*', 2);
        operatorLevel.put('/', 2);

        // 保存运算符对应的运算操作
        operatorMethod = new HashMap<Character, Method>();
        Class<Operator> clazz = Operator.class;
        try {
            operatorMethod.put('+', clazz.getMethod("add", int.class, int.class));
            operatorMethod.put('-', clazz.getMethod("sub", int.class, int.class));
            operatorMethod.put('*', clazz.getMethod("multi", int.class, int.class));
            operatorMethod.put('/', clazz.getMethod("divide", int.class, int.class));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOperator(char o){
        return operatorLevel.containsKey(o);
    }

    public static int getLevel(char o){
        return operatorLevel.get(o);
    }

    public static int calculate(char o, int left, int right) throws InvocationTargetException, IllegalAccessException {
        return (int)operatorMethod.get(o).invoke(null, left, right);
    }

    // 四则运算方法
    public static int add(int left, int right){
        return left + right;
    }
    public static int sub(int left, int right){
        return left - right;
    }
    public static int multi(int left, int right){
        return left * right;
    }
    public static int divide(int left, int right){
        return left / right;
    }
}
