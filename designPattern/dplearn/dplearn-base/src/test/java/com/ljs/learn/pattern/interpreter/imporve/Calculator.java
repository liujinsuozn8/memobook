package com.ljs.learn.pattern.interpreter.imporve;

import java.util.HashMap;
import java.util.Stack;

// 计算器，类似于 Context
public class Calculator {
    // 定义表达式
    private Expression expression;

    public Calculator(String expStr) {
        Stack<Expression> stack = new Stack<>();
        // 将表达式拆分成字符数组
        char[] chars = expStr.toCharArray();

        Expression left = null;
        Expression right = null;

        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '+':
                    // 从栈中获取左值
                    left = stack.pop();
                    // 创建右值，并调整遍历的index
                    right = new VarExpression(String.valueOf(chars[++i]));
                    // 创建加法表达式，并保存到栈
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    // 从栈中获取左值
                    left = stack.pop();
                    // 创建右值
                    right = new VarExpression(String.valueOf(chars[++i]));
                    // 创建减法表达式，并保存到栈
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    // 参数部分
                    stack.push(new VarExpression(String.valueOf(chars[i])));
                    break;
            }
        }
        // 遍历后得到最后一个表达式对象，
        this.expression = stack.pop();
    }

    public int run(HashMap<String,Integer> var){
        // var 仍然是 {a=1, b=3, c=5} 形式的运算参数集合
        return this.expression.interpret(var);
    }
}
