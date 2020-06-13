package com.ljs.learn.pattern.interpreter.imporve;

import java.util.HashMap;


// 加法解释器
public class AddExpression extends SymbolExpression {
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    // 处理加法运算
    // var 仍然是 {a=1, b=3, c=5} 形式的运算参数集合
    @Override
    public int interpret(HashMap<String, Integer> var) {
        return super.left.interpret(var) + super.right.interpret(var);
    }
}
