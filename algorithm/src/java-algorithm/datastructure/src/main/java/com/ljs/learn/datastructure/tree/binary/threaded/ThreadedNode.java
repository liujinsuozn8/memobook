package com.ljs.learn.datastructure.tree.binary.threaded;

import java.util.Objects;

// 中序遍历线索二叉树结点
public class ThreadedNode<T>{
    T data;
    ThreadedNode<T> left;
    ThreadedNode<T> right;

    // leftType == 0 表示指向的是左子树
    // leftType == 1 表示指向的中序遍历的前驱结点
    int rightType;
    // rightType == 0 表示指向的是右子树
    // rightType == 1 表示指向的中序遍历的后继结点
    int leftType;

    public ThreadedNode(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadedNode<?> that = (ThreadedNode<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
