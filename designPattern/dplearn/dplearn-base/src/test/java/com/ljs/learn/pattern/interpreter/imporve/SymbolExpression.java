package com.ljs.learn.pattern.interpreter.imporve;

/**
 * 非终结符解析器
 * 抽象运算符号解析器
 * 每个运算符都只和自己左右两个数字有关系，但左右两个数字有可能也是一个解析的结果，
 * 无论何种类型，都是Expression的实现类
 * */
public abstract class SymbolExpression extends Expression {
    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
