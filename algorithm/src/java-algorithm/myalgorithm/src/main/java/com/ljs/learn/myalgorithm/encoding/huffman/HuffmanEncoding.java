package com.ljs.learn.myalgorithm.encoding.huffman;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoding implements Serializable {
    // 编码表
    public Map<Byte, String> codeTable = new HashMap<>();
    // 编码后的byte数组
    public byte[] zipBytes;
    // 编码后的长度
    public int codeLength;

}
