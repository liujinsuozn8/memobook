package com.ljs.learn.pattern.interpreter.imporve;

import java.util.HashMap;

// 终结符解析器
// 变量解析器
public class VarExpression extends Expression {
    // key 就是公式中的参数
    // 如公式是：a+b+c
    // key就是：a、b、c
    private String key;

    public VarExpression(String key) {
        this.key = key;
    }

    // var 是 {a=1, b=3, c=5}
    // 根据对象内部的key，返回对应值
    @Override
    public int interpret(HashMap<String, Integer> var) {
        return var.get(key);
    }
}
