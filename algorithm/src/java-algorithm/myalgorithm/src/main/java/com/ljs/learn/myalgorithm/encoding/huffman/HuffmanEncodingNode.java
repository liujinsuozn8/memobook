package com.ljs.learn.myalgorithm.encoding.huffman;

public class HuffmanEncodingNode implements Comparable<HuffmanEncodingNode> {
    int weight;
    Byte data;  //为了能够在构建霍夫曼树时，保存数据 null，需要使用包装类
    HuffmanEncodingNode left;
    HuffmanEncodingNode right;

    public HuffmanEncodingNode(int weight, Byte data) {
        this.weight = weight;
        this.data = data;
    }

    @Override
    public String toString() {
        return "HuffmanEncodingNode{" +
                "weight=" + weight +
                ", data=" + data +
                '}';
    }

    @Override
    public int compareTo(HuffmanEncodingNode o) {
        return this.weight - o.weight;
    }
}
