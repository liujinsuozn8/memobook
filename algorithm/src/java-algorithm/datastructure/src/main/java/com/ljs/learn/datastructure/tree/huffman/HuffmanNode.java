package com.ljs.learn.datastructure.tree.huffman;

// 为了能够对结点排序，需要实现 Comparable 接口
public class HuffmanNode implements Comparable<HuffmanNode> {
    int weight; //结点的权值
    HuffmanNode left;
    HuffmanNode right;

    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null){
            this.right.preOrder();
        }
    }

    public HuffmanNode(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "weight=" + weight +
                '}';
    }


    // 比较方法用于排序
    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight - o.weight;
    }
}
