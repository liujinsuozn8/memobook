package com.ljs.learn.pattern.prototype.deepCopy.type01;

import org.junit.Test;

public class ClientTest {
    @Test
    public void deepCopyType01(){
        DeepProtoType d1 = new DeepProtoType("aa", new DeepCloneableTarget("bb", "cc"));
        DeepProtoType clone1 = (DeepProtoType) d1.clone();

        System.out.println(d1.target.hashCode() == clone1.target.hashCode());
    }
}
