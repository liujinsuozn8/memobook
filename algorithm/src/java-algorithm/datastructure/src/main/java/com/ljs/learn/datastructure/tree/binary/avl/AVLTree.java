package com.ljs.learn.datastructure.tree.binary.avl;

public class AVLTree {
    protected AVLNode root;

    // 返回树的高度
    public int height(){
        if (root == null) return 0;
        return root.height();
    }

    // 添加数组，每次添加元素时，都通过旋转进行结构优化
    public void addArrayWithRotate(int[] array){
        for (int n : array) {
            addNodeWithRotate(n);
        }
    }

    // 添加元素，每次添加时，都通过旋转进行结构优化
    public void addNodeWithRotate(int value){
        if (root == null) {
            root = new AVLNode(value);
        } else {
            root.addWithRotate(new AVLNode(value));
        }
    }

    // 将数组的每个元素添加到树中
    public void addArray(int[] array) {
        for (int n : array) {
            addNode(n);
        }
    }

    // 添加结点
    public void addNode(int value) {
        if (root == null) {
            root = new AVLNode(value);
        } else {
            root.add(new AVLNode(value));
        }
    }



    // 中序遍历
    public void infixOrder() {
        if (root == null) {
            System.out.println("empty tree");
        } else {
            root.infixOrder();
        }
    }

    // 查找结点
    public AVLNode searchNode(int value) {
        if (root == null) return null;
        return root.searchNode(value);
    }

    // 查找父节点
    public AVLNode searchParentNode(int value) {
        if (root == null) return null;
        return root.searchParentNode(value);
    }

    // 删除结点
    public void deleteNode(int value) {
        if (root == null) return;
        // 1. 搜索结点
        // 1.1 搜索目标结点
        AVLNode targetNode = searchNode(value);
        // 如果没有找到则返回
        if (targetNode == null) return;

        // 1.2 搜索父节点
        AVLNode parentNode = searchParentNode(value);

        if (parentNode == null) {
            // 2. 如果该结点没有父节点，则说明该结点是根结点
            // 2.1 如果左右子结点都是 null，则将root设为空
            if (targetNode.left == null && targetNode.right == null) {
                root = null;
            } else if (targetNode.left != null && targetNode.right != null) {
                // 2.2 如果左右子节点都存在
                // 获取右子树的最小值，并删除该结点
                int rightMinVal = delRightTreeMin(targetNode.right);
                // 将当前结点的值替换为右子树的最小值
                targetNode.value = rightMinVal;
            } else {
                // 2.3 如果只有左子树或只有右子树，则将根结点替换为子树
                if (targetNode.left != null) {
                    root = targetNode.left;
                } else if (targetNode.left != null) {
                    root = targetNode.right;
                }
            }
        } else {
            // 3. 删除叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                // 判断目标结点是父节点的左结点还是右结点
                if (targetNode == parentNode.left) {
                    parentNode.left = null;
                } else {
                    parentNode.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                // 5. 删除有两颗子树的结点
                // 5.1 获取右子树的最小值，并删除该结点
                int rightMinVal = delRightTreeMin(targetNode.right);
                // 5.2 将当前结点的值替换为右子树的最小值
                targetNode.value = rightMinVal;
            } else {
                // 4. 删除只有一个子树的结点
                if (targetNode.left != null) {
                    // 4.1 如果目标结点只有左子树
                    if (parentNode.left == targetNode) {
                        parentNode.left = targetNode.left;
                    } else {
                        parentNode.right = targetNode.left;
                    }
                } else {
                    // 4.2 如果目标结点只有右子树
                    if (parentNode.left == targetNode) {
                        parentNode.left = targetNode.right;
                    } else {
                        parentNode.right = targetNode.right;
                    }
                }
            }
        }
    }

    /**
     * 1. 获取以 node 为根结点的子树的最小结点
     * 2. 删除node为结点的二叉排序树的最小结点
     *
     * @param node 二叉排序树结点
     * @return 返回以node为根结点的二叉树的最小结点的值
     */
    public int delRightTreeMin(AVLNode node) {
        AVLNode target = node;

        // 1. 循环查找左结点，找到最小结点
        while (target.left != null) {
            target = target.left;
        }

        // 2. 删除该结点
        deleteNode(target.value);
        return target.value;
    }
}
