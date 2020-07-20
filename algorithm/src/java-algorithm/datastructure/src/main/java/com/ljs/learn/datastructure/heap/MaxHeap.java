package com.ljs.learn.datastructure.heap;

public class MaxHeap {

    /** 1. 启动大顶堆的构建，构建整个树
     *
     * @param array 需要构建大顶堆的数组
     */
    public static void adjustHeap(int[] array){
        adjustHeap(array, array.length);
    }

    /** 2. 启动大顶堆的构建，可以部分构建
     *
     * @param array 需要构建大顶堆的数组
     * @param length 构建的最大长度，可以设置只将树的一部分构建为大顶堆
     */
    public static void adjustHeap(int[] array, int length){
        // 1. 计算最后一个非叶子结点的索引
        int lastNoLeafNodeIdx = array.length / 2 - 1;

        // 2. 从后向前遍历所有非叶子结点
        for (int i = lastNoLeafNodeIdx; i >= 0 ; i--) {
            adjustHeapForSubTree(array, i, length);
        }
    }

    /**
     * 3. 将 i 结点开始的子树调整为一个大顶堆
     * @param array 待调整的数组
     * @param i   表示非叶子结点在数组中的索引
     * @param length 对多少个元素进行调整
     */
    public static void adjustHeapForSubTree(int array[], int i, int length){
        // 1. 取出当前非叶子结点的值，并保存。在整体调整完成之后，设置到最终的索引处
        int temp = array[i];

        // 2. 开始调整
        // 从当前结点的左结点开始
        for (int k = i*2+1; k<length; k=k*2+1){
            // 2.1 如果还有右子节点，并且右子节点更大，将k指向右子节点
            // 否则使用左结点
            if (k+1<length && array[k] < array[k+1]){
                k++;
            }

            // 2.2 如果子节点大于 temp，交换位置
            if (array[k] > temp){
                array[i] = array[k];
                i=k;
            } else {
                // 否则，整个子树已经是大顶堆，退出构建
                break;
            }
        }

        // 3. 以i为父节点子树构建完成，将temp保存到调整后的位置
        array[i] = temp;
    }
}
