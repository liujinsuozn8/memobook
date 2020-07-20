package com.ljs.learn.datastructure.tree.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {

    // 创建霍夫曼树
    public static HuffmanNode createTree(int[] array) {
        // 将数组转换为结点数组
        List<HuffmanNode> nodes = new ArrayList<>(array.length);
        for (int i : array) {
            nodes.add(new HuffmanNode(i));
        }

        while (nodes.size() > 1) {
            // 1. 将结点数组进行排序（升序）
            Collections.sort(nodes);

            // 2. 抽取最小的两个结点，并从数组中删除
            HuffmanNode left = nodes.get(0);
            HuffmanNode right = nodes.get(1);

            // 3. 使用两个最小结点创建新的树
            HuffmanNode newNode = new HuffmanNode(left.weight + right.weight);
            newNode.left = left;
            newNode.right = right;

            // 4. 将新的树保存到数组中
            // 4.1 方式1，将树设置到index=1的位置直接替换旧的树。然啊后删除index=0的树
            nodes.set(1, newNode);
            nodes.remove(0);
            // 4.2 方式2，删除第一个和第二个元素，然后将新的树添加到数组末尾
            // nodes.remove(left);
            // nodes.remove(right);
            // nodes.add(newNode);

            // 5. 重新循环：排序-->抽最小的两个值-->创建树-->保存树
            // 直到数组中只剩下一个元素
        }

        return nodes.get(0);
    }
}
