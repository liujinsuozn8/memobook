package com.ljs.learn.datastructure.tree.binary.arrbinary;

// 顺序存储二叉树
public class ArrayBinaryTree {
    // 存储结点的数组
    private int[] array;

    public ArrayBinaryTree(int[] array) {
        this.array = array;
    }

    /**
     * 启动前序遍历
     */
    public void preOrder(){
        // 如果数组为空，或者 arr.length = 0，则退出
        if(array == null || array.length == 0){
            System.out.println("null array");
            return;
        }
        this.preOrder(0);
    }

    /** 完成顺序存储二叉树的前序遍历
     * @param index 数组下标
     */
    public void preOrder(int index){
        // 1. 输出当前的元素
        System.out.println(array[index]);

        // 2. 向左递归遍历，需要防止越界
        if ((index * 2 + 1) < this.array.length){
            preOrder(index * 2 + 1);
        }

        // 3. 向右递归遍历，需要防止越界
        if ((index * 2 + 2) < this.array.length){
            preOrder(index * 2 + 2);
        }
    }

    // 启动中序遍历
    public void infixOrder(){
        // 如果数组为空，或者 arr.length = 0，则退出
        if(array == null || array.length == 0){
            System.out.println("null array");
            return;
        }
        this.infixOrder(0);
    }
    // 中序遍历
    public void infixOrder(int index){
        if (index * 2 + 1 < array.length){
            infixOrder(index * 2 + 1);
        }

        System.out.println(array[index]);

        if (index * 2 + 2 < array.length){
            infixOrder(index * 2 + 2);
        }
    }

    // 启动后续遍历
    public void postOrder(){
        // 如果数组为空，或者 arr.length = 0，则退出
        if(array == null || array.length == 0){
            System.out.println("null array");
            return;
        }
        postOrder(0);
    }
    // 后续遍历
    public void postOrder(int index){
        if (index * 2 + 1 < array.length){
            postOrder(index * 2 + 1);
        }

        if (index * 2 + 2 < array.length){
            postOrder(index * 2 + 2);
        }

        System.out.println(array[index]);
    }
}
