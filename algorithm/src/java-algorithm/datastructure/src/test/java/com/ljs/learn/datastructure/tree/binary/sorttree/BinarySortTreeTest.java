package com.ljs.learn.datastructure.tree.binary.sorttree;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySortTreeTest {
    // 二叉排序树构建测试
    @Test
    public void addArray() {
        int[] array = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        binarySortTree.addArray(array);

        binarySortTree.infixOrder();
        // 输出: 1 3 5 7 9 10 12
    }

    // 删除叶子结点
    @Test
    public void deleteLeafNode(){
        int[] array = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        binarySortTree.addArray(array);
        binarySortTree.infixOrder();
        System.out.println("------------");

        binarySortTree.deleteNode(1);
        binarySortTree.deleteNode(9);
        binarySortTree.infixOrder();

        // 输出: 3 5 7 10 12
    }

    // 删除只有一颗子树的结点
    @Test
    public void deleteOneSubNode(){
        int[] array = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        binarySortTree.addArray(array);
        binarySortTree.infixOrder();
        System.out.println("------------");

        binarySortTree.deleteNode(1);
        binarySortTree.infixOrder();

        // 输出: 2 3 5 7 9 10 12
    }

    // 删除包含两颗子树的结点
    @Test
    public void deleteDoubleSubNode(){
        int[] array = {7, 3, 10, 12, 5, 1, 9, 11, 13};
        BinarySortTree binarySortTree = new BinarySortTree();
        binarySortTree.addArray(array);
        binarySortTree.infixOrder();
        System.out.println("------------");

        binarySortTree.deleteNode(10);
        binarySortTree.infixOrder();

        // 输出: 1 3 5 7 9 11 12 13
    }
}