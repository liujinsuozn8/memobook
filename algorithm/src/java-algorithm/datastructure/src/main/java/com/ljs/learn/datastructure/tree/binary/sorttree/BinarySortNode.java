package com.ljs.learn.datastructure.tree.binary.sorttree;

// 二叉排序树结点
public class BinarySortNode {
    int value;
    BinarySortNode left;
    BinarySortNode right;

    public BinarySortNode(int value) {
        this.value = value;
    }

    // 添加结点
    // 以递归的形式添加结点，需要满足二叉排序树的规则
    public void add(BinarySortNode node) {
        if (node == null) {
            return;
        }

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
    public BinarySortNode searchNode(int value) {
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
    public BinarySortNode searchParentNode(int value) {
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
