package com.ljs.learn.datastructure.tree.binary.demo1;

public class BinaryTree<T> {
    private TreeNode<T> root; // 保存根结点

    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    // 启动前序遍历
    public void preOrder(){
        if (root == null) return ;
        root.preOrder();
    }

    // 启动中序遍历
    public void infixOrder(){
        if (root == null) return ;
        root.infixOrder();
    }

    // 启动后序遍历
    public void postOrder(){
        if (root == null) return ;
        root.postOrder();
    }

    // 启动前序查找
    public T preOrderSearch(T findVal){
        if (root == null) return null;
        return root.preOrderSearch(findVal);
    }
    // 启动中序查找
    public T infixOrderSearch(T findVal){
        if (root == null) return null;
        return root.infixOrderSearch(findVal);
    }
    // 启动后序查找
    public T postOrderSearch(T findVal){
        if (root == null) return null;
        return root.postOrderSearch(findVal);
    }

    // 启动删除结点
    public void delNode(int findVal){
        // 1. 如果是空树，则跳过
        if (root == null) {
            return ;
        } else if ( root.getData().equals(findVal)){
            // 2. 检查root结点，如果 `root结点 == findVal`，则将二叉树置空
            root = null;
        } else {
            root.delNode(findVal);
        }
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }
}
