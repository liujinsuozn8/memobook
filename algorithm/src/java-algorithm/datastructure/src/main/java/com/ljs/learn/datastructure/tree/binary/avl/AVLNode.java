package com.ljs.learn.datastructure.tree.binary.avl;

// 二叉排序树结点
public class AVLNode {
    int value;
    AVLNode left;
    AVLNode right;

    public AVLNode(int value) {
        this.value = value;
    }

    // 返回以当前结点为根结点的树的高度
    public int height() {
        // 比较左右子树的高度，返回最大的高度
        return Math.max(
                left == null ? 0 : left.height(),
                right == null ? 0 : right.height()
        ) + 1;  // +1 表示当前结点作为根结点也算一层
    }

    // 返回左子树的高度
    public int leftHeight() {
        if (left == null) return 0;
        return left.height();
    }

    // 返回右子树的高度
    public int rightHeight() {
        if (right == null) return 0 ;
        return right.height();
    }

    // 左旋
    public void leftRotate(){
        AVLNode newNode = new AVLNode(this.value);
        newNode.left = this.left;
        newNode.right = this.right.left;
        this.value = this.right.value;
        this.right = this.right.right;
        this.left = newNode;
    }

    // 右旋
    public void rightRotate(){
        AVLNode newNode = new AVLNode(this.value);
        newNode.right = this.right;
        newNode.left = this.left.right;
        this.value = this.left.value;
        this.left = this.left.left;
        this.right = newNode;
    }

    // 添加结点，并且每次添加后，检查子树高度，并通过旋转来优化树结构
    public void addWithRotate(AVLNode node){
        // 判断结点与当前结点的大小
        if (node.value < this.value) {
            // 如果当前结点的左子节点为null，则直接添加结点
            if (this.left == null) {
                this.left = node;
            } else {
                // 递归的向左子树添加
                this.left.add(node);
            }
        } else {
            //
            if (this.right == null) {
                this.right = node;
            } else {
                // 递归的向右子树添加
                this.right.add(node);
            }
        }

        // 检查子树高度
        int leftTreeHeight = leftHeight();
        int rightTreeHeight = rightHeight();
        if ((rightTreeHeight - leftTreeHeight) > 1){
            // 左子树 < 右子树，则左旋

            // 双旋检查: 在右子树中，如果子树的左子树高度 > 子树的右子树的高度，则右旋
            if (right != null && right.leftHeight() > right.rightHeight()){
                right.rightRotate();
            }

            // 再做左旋
            leftRotate();
        } else if ((leftTreeHeight - rightTreeHeight) > 1){
            // 左子树 > 右子树，则右旋

            // 双旋检查: 在左子树中，如果子树的左子树高度 < 子树的右子树的高度，则左旋
            if (left != null && left.rightHeight() > left.leftHeight()){
                left.leftRotate();
            }

            // 再做右旋
            rightRotate();
        }
    }


    // 添加结点
    // 以递归的形式添加结点，需要满足二叉排序树的规则
    public void add(AVLNode node) {
        // 判断结点与当前结点的大小
        if (node.value < this.value) {
            // 如果当前结点的左子节点为null，则直接添加结点
            if (this.left == null) {
                this.left = node;
            } else {
                // 递归的向左子树添加
                this.left.add(node);
            }
        } else {
            //
            if (this.right == null) {
                this.right = node;
            } else {
                // 递归的向右子树添加
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this.value);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    // 在树中搜索需要删除的结点 targetNode
    public AVLNode searchNode(int value) {
        if (this.value == value) {   // 如果和目标值相等，在直接返回
            return this;
        } else if (value < this.value) { // 如果比当前值小，则继续左继续递归搜索
            if (this.left == null) return null;
            return this.left.searchNode(value);
        } else { // 如果比当前值大，则继续右继续递归搜索
            if (this.right == null) return null;
            return this.right.searchNode(value);
        }
    }

    // 找到 targetNode 的父节点 parent
    public AVLNode searchParentNode(int value) {
        // 如果当前结点的左结点或右结点等于 value，则找到父节点
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)) {
            return this;
        }

        if (value < this.value && this.left != null) {
            // 如果value比当前结点小，则继续左递归
            return this.left.searchParentNode(value);
        } else if (value > this.value && this.right != null){
            // 如果value比当前结点大，则继续左递归
            return this.right.searchParentNode(value);
        } else {
            return null; // 无法找到父节点
        }
    }


    @Override
    public String toString() {
        return "BinarySortNode{" +
                "value=" + value +
                '}';
    }
}
