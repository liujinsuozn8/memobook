package com.ljs.learn.pattern.interpreter.imporve;

import java.util.HashMap;

// 抽象类表达式，通过HashMap键值对，可以获取到变量的值
public abstract class Expression {
    // 解释公式和数值，key就是公式中的参数
    // 如公式是 a+b+c
    // HashMap是：{a:1, b:3, c:5}
    public abstract int interpret(HashMap<String, Integer> var);
}
