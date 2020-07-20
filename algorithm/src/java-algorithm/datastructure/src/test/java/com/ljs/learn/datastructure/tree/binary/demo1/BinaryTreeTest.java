package com.ljs.learn.datastructure.tree.binary.demo1;

import com.ljs.learn.datastructure.tree.binary.demo1.BinaryTree;
import com.ljs.learn.datastructure.tree.binary.demo1.TreeNode;
import org.junit.Test;

public class BinaryTreeTest {
    public static BinaryTree<Integer> tree;
    static {
        /** 创建一个二叉树
         *        1
         *     2     3
         *   4      5   6
         */
        TreeNode<Integer> node1 = new TreeNode<>(1);
        TreeNode<Integer> node2 = new TreeNode<>(2);
        TreeNode<Integer> node3 = new TreeNode<>(3);
        TreeNode<Integer> node4 = new TreeNode<>(4);
        TreeNode<Integer> node5 = new TreeNode<>(5);
        TreeNode<Integer> node6 = new TreeNode<>(6);
        node1.setLeft(node2);
        node1.setRight(node3);
        node2.setLeft(node4);
        node3.setLeft(node5);
        node3.setRight(node6);

        tree = new BinaryTree<>(node1);
    }

    // 前序遍历
    @Test
    public void preOrder() {
        tree.preOrder();
        // 输出: 1 2 4 3 5 6
    }

    // 中序遍历
    @Test
    public void infixOrder() {
        tree.infixOrder();
        //输出: 4 2 1 3 5 6
    }

    // 后序遍历
    @Test
    public void postOrder() {
        tree.postOrder();
        //输出: 4 2 5 6 3 1
    }

    // 前序查找测试
    @Test
    public void preOrderSearch(){
        assert tree.preOrderSearch(5) == 5;
        assert tree.preOrderSearch(7) == null;
    }

    // 中序查找测试
    @Test
    public void infixOrderSearch(){
        assert tree.infixOrderSearch(5) == 5;
        assert tree.infixOrderSearch(7) == null;
    }

    // 后序查找测试
    @Test
    public void postOrderSearch(){
        assert tree.postOrderSearch(5) == 5;
        assert tree.postOrderSearch(7) == null;
    }

    // 删除结点测试
    @Test
    public void delNode(){
        tree.preOrder();
        // 输出: 1 2 4 3 5 6

        // 删除结点
        tree.delNode(5);

        System.out.println("------");

        tree.preOrder();
        // 输出: 1 2 4 3 6
    }
}
