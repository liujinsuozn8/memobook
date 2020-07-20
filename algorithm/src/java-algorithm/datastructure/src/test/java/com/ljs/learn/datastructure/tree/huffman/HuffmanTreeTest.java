package com.ljs.learn.datastructure.tree.huffman;

import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanTreeTest {

    @Test
    public void createTree() {
        // 创建树并指向前序遍历
        int[] array = {1,2,3,4,5,6};
        HuffmanNode tree = HuffmanTree.createTree(array);

        tree.preOrder();
        // 21 9 4 5 12 6 3 1 2 3 6
    }
}