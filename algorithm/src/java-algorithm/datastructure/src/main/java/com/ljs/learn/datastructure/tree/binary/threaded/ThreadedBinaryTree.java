package com.ljs.learn.datastructure.tree.binary.threaded;

// 中序遍历线索二叉树
public class ThreadedBinaryTree<T> {
    private ThreadedNode<T> root;

    // 为了实现线索化，需要创建指向当前结点的前驱结点的指针
    // 在递归进行线索化时，总是保存前一个结点
    private ThreadedNode<T> pre = null;

    public ThreadedBinaryTree(ThreadedNode<T> root) {
        this.root = root;
    }

    // 启动二叉树中序线索化
    public void infixThreadedNodes(){
        this.infixThreadedNodes(root);
    }

    /**
     * 对二叉树进行中序线索化
     * @param node 需要线索化的结点
     */
    public void infixThreadedNodes(ThreadedNode<T> node){
        // 如果是空结点，则跳过
        if (node == null) return ;

        // 1. (递归的)线索化左子树
        infixThreadedNodes(node.left);

        // 2. 线索化当前结点
        // 2.1 处理【当前结点】的前驱结点
        // 如果当前结点的左结点为空，则线索化
        if (node.left == null){
            // 让当前结点的左指针指向前驱结点
            node.left = pre;
            // 设置左指针的类型为线索
            node.leftType = 1;
        }

        // 2.2 处理【前驱结点】的后继结点（因为当前结点无法处理后继结点，所以放在再一次迭代中处理）
        // 如果前驱结点的右结点为空，则线索化
        // 需要防止第一次为空
        if (pre != null && pre.right == null){
            // 将前驱结点的右结点设置为当前结点
            pre.right = node;
            pre.rightType = 1;
        }

        // 每处理一个个结点后，将前驱结点更新为当前结点
        pre = node;

        // 3. (递归的)线索化右子树
        infixThreadedNodes(node.right);
    }

    // 遍历中序线索化二叉树
    public void infixThreadedList(){
        // 存储当前遍历的结点，从root开始
        ThreadedNode<T> node = root;

        // 当 node == null 时，即遍历到终点
        while (node != null){
            // 1. 循环找到leftType == 1的结点
            while (node.leftType == 0) node = node.left;

            // 打印当前结点
            System.out.println(node);

            // 如果当前结点的右指针指向的是后继结点，就一直输出
            while(node.rightType == 1){
                node = node.right;
                System.out.println(node);
            }

            // 更新结点
            node = node.right;
        }
    }
}
