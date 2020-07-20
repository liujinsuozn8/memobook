package com.ljs.learn.datastructure.tree.binary.demo1;

public class TreeNode<T> {
    private T data;
    private TreeNode left;  // 左结点
    private TreeNode right; // 右结点

    public TreeNode(T data) {
        this.data = data;
    }

    // 遍历算法
    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    // 后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }

        if (this.right != null) {
            this.right.postOrder();
        }

        System.out.println(this);
    }

    // 查找算法
    // 前序查找
    public T preOrderSearch(T findVal) {
        // System.out.println(1);

        // 1. 与当前结点比较，相同则返回
        if (this.data.equals(findVal)) {
            return this.data;
        }

        T temp = null;
        // 2. 判断左子树是否为空，不为空，则向左递归前序遍历
        if (this.left != null) {
            temp = (T) this.left.preOrderSearch(findVal);

            // 如果找到则返回
            if (temp != null) return temp;
        }

        // 3. 如果左边没找到，则判断右子树是否为空，不为空则向右递归前序遍历
        if (this.right != null) {
            temp = (T) this.right.preOrderSearch(findVal);
        }

        // 4. 不对结果进行判断，直接返回，有上一层的遍历来进行判断
        return temp;
    }

    // 中序查找
    public T infixOrderSearch(T findVal) {
        // System.out.println(2);

        T temp = null;

        // 1. 判断左子树是否为空，不为空则向左递归遍历
        if (this.left != null) {
            temp = (T) this.left.infixOrderSearch(findVal);

            // 如果找到则返回
            if (temp != null) return temp;
        }

        // 2. 与当前结点比较，相同则返回
        if (this.data.equals(findVal)) {
            return this.data;
        }

        // 3. 判断右子树是否为空，不为空则向右递归遍历
        if (this.right != null) {
            temp = (T) this.right.infixOrderSearch(findVal);
        }

        // 4. 不对结果进行判断，直接返回，有上一层的遍历来进行判断
        return temp;
    }

    // 后续查找
    public T postOrderSearch(T findVal) {
        // System.out.println(3);

        T temp = null;

        // 1. 判断左子树是否为空，不为空则向左递归遍历
        if (this.left != null) {
            temp = (T) this.left.postOrderSearch(findVal);

            // 如果找到则返回
            if (temp != null) return temp;
        }

        // 2. 判断右子树是否为空，不为空则向右递归遍历
        if (this.right != null) {
            temp = (T) this.right.postOrderSearch(findVal);

            // 如果找到则返回
            if (temp != null) return temp;
        }

        // 3. 与当前结点比较，相同则返回
        if (this.data.equals(findVal)) {
            return this.data;
        } else {
            // 不同则直接返回null
            return null;
        }
    }

    // 删除结点
    public void delNode(int findVal){
        // 1. 如果: `左子节点 != null`，并且`左子节点 == findVal`，删除左子节点
        if (this.left != null && this.left.getData().equals(findVal)){
            this.left = null;
        }

        // 2. 如果: `右子节点 != null`，并且`右子节点 == findVal`，删除右子节点
        if (this.right != null && this.right.getData().equals(findVal)){
            this.right = null;
        }

        // 3. 如果没有找到目标结点，并且 `左子节点 != null`，继续向左递归删除
        if (this.left != null){
            this.left.delNode(findVal);
        }
        // 4. 如果左递归删除没有成功，并且 `右子节点 != null`，继续向右递归删除
        if (this.right != null){
            this.right.delNode(findVal);
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
