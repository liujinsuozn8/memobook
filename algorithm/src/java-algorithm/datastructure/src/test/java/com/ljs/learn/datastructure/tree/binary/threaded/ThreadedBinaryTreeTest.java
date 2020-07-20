package com.ljs.learn.datastructure.tree.binary.threaded;

import org.junit.Test;

import static org.junit.Assert.*;

// 线索化二叉树
public class ThreadedBinaryTreeTest {

    // 线索化测试
    @Test
    public void infixThreadedNodes() {
        // 创建二叉树 [1, 3, 6, 8, 10, 14]
        ThreadedNode<Integer> node1 = new ThreadedNode<>(1);
        ThreadedNode<Integer> node3 = new ThreadedNode<>(3);
        ThreadedNode<Integer> node6 = new ThreadedNode<>(6);
        ThreadedNode<Integer> node8 = new ThreadedNode<>(8);
        ThreadedNode<Integer> node10 = new ThreadedNode<>(10);
        ThreadedNode<Integer> node14 = new ThreadedNode<>(14);

        node1.left = node3;
        node1.right = node6;
        node3.left = node8;
        node3.right = node10;
        node6.left = node14;

        ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<>(node1);

        //执行线索化
        tree.infixThreadedNodes();

        assertEquals( node10.left, node3);
        assertEquals( node10.right, node1);

        assertEquals( node14.left, node1);
        assertEquals( node14.right, node6);

        // 中序遍历时 8 第一个执行线索化，pre = null，
        assertEquals(node8.left, null);
        assertEquals(node8.right, node3);
    }

    // 遍历测试
    @Test
    public void infixThreadedList(){
        // 创建二叉树 [1, 3, 6, 8, 10, 14]
        ThreadedNode<Integer> node1 = new ThreadedNode<>(1);
        ThreadedNode<Integer> node3 = new ThreadedNode<>(3);
        ThreadedNode<Integer> node6 = new ThreadedNode<>(6);
        ThreadedNode<Integer> node8 = new ThreadedNode<>(8);
        ThreadedNode<Integer> node10 = new ThreadedNode<>(10);
        ThreadedNode<Integer> node14 = new ThreadedNode<>(14);

        node1.left = node3;
        node1.right = node6;
        node3.left = node8;
        node3.right = node10;
        node6.left = node14;

        ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<>(node1);

        //执行线索化
        tree.infixThreadedNodes();

        // 遍历线索化二叉树
        tree.infixThreadedList();
        // 输出：8, 3, 10, 1, 14, 6
    }
}