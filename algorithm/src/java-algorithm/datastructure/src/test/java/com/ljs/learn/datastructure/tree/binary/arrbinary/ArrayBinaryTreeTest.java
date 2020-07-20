package com.ljs.learn.datastructure.tree.binary.arrbinary;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayBinaryTreeTest {

    // 顺序存储二叉树
    // 前序遍历测试
    @Test
    public void preOrder() {
        ArrayBinaryTree tree = new ArrayBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        tree.preOrder();
        // 输出: 1 2 4 5 3 6 7
    }

    // 中序遍历测试
    @Test
    public void infixOrder(){
        ArrayBinaryTree tree = new ArrayBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        tree.infixOrder();
        // 输出 4 2 5 1 6 3 7
    }

    // 后续遍历测试
    @Test
    public void postOrder(){
        ArrayBinaryTree tree = new ArrayBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        tree.postOrder();
        // 输出 4 5 2 6 7 3 1
    }
}