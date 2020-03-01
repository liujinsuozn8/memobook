package com.ljs.learn.pattern.prototype.deepCopy.type01;

import java.io.*;

public class DeepProtoType implements Serializable, Cloneable {
    private static final long serialVersionUID = 1940210468835235379L;

    public String name;
    // 一个引用类型的成员属性
    public DeepCloneableTarget target;

    public DeepProtoType(String name, DeepCloneableTarget target) {
        this.name = name;
        this.target = target;
    }

    // 深拷贝方式1：重写clone()方法
    @Override
    protected Object clone()  {
        // 克隆自身的基本数据类型的成员对象
        DeepProtoType result = null;
        try {
            result = (DeepProtoType) super.clone();
            // 逐一克隆引用数据类型的成员对象
            result.target = (DeepCloneableTarget) target.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
