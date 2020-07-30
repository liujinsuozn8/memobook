package com.ljs.learn.datastructure.tree.binary.avl;

import org.junit.Test;

import static org.junit.Assert.*;

public class AVLTreeTest {
    // 二叉排序树构建测试
    @Test
    public void addArray() {
        int[] array = {4, 3, 6, 5, 7, 8};
        AVLTree avlTree = new AVLTree();
        avlTree.addArray(array);

        avlTree.infixOrder();
        // 输出: 3 4 5 6 7 8
    }

    // 测试子树高度
    @Test
    public void height(){
        int[] array = {4, 3, 6, 5, 7, 8};
        AVLTree avlTree = new AVLTree();
        avlTree.addArray(array);

        assertEquals(avlTree.height(), 4);
    }

    // 二叉平衡树--左旋测试
    @Test
    public void leftRotate(){
        int[] array = {4, 3, 6, 5, 7, 8};
        AVLTree avlTree = new AVLTree();
        avlTree.addArrayWithRotate(array);

        avlTree.infixOrder();
        System.out.println("left height = " + avlTree.root.leftHeight());
        System.out.println("right height = " + avlTree.root.rightHeight());
    }

    // 二叉平衡树--右旋测试
    @Test
    public void rightRotate(){
        int[] array = {10, 12, 8, 9, 7, 6};
        AVLTree avlTree = new AVLTree();
        avlTree.addArrayWithRotate(array);

        avlTree.infixOrder();
        System.out.println("left height = " + avlTree.root.leftHeight());
        System.out.println("right height = " + avlTree.root.rightHeight());
    }

    // 二叉平衡树--右旋测试
    @Test
    public void rightRotate2(){
        int[] array = {10, 11, 7, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        avlTree.addArrayWithRotate(array);

        avlTree.infixOrder();
        System.out.println("left height = " + avlTree.root.leftHeight());
        System.out.println("right height = " + avlTree.root.rightHeight());
    }
}