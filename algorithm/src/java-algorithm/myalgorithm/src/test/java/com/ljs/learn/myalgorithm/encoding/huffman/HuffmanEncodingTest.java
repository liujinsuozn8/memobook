package com.ljs.learn.myalgorithm.encoding.huffman;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HuffmanEncodingTest {

    // 构建编码表
    @Test
    public void createCodeTable() {
        // String data = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Vero ipsum commodi maiores magnam sed blanditiis iste labore, provident sint! Ratione assumenda, quae modi corporis soluta error tempore odit enim impedit.";
        String data = "i like like like java do you like a java";
        // 1. 创建结点
        List<HuffmanEncodingNode> nodes = HuffmanEncodingUtils.getNodes(data);

        // 2. 构建霍夫曼树
        HuffmanEncodingNode root = HuffmanEncodingUtils.createTree(nodes);
        System.out.println(root);
        // 3. 构建编码表
        Map<Byte, String> codeTable = HuffmanEncodingUtils.createCodeTable(root);
        System.out.println(codeTable);
    }

    // 编码测试
    @Test
    public void encode() {
        // String data = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Vero ipsum commodi maiores magnam sed blanditiis iste labore, provident sint! Ratione assumenda, quae modi corporis soluta error tempore odit enim impedit.";
        String data = "i like like like java do you like a java";
        HuffmanEncoding encode = HuffmanEncodingUtils.encode(data);

        System.out.println(Arrays.toString(encode.zipBytes));
    }

    // byte数转字符串测试
    @Test
    public void byteToString(){
        String str = HuffmanEncodingUtils.byteToString((byte) -49);
        // 原码: -49 ---> 1011 0001
        // 反码      ---> 1100 1110
        // 补码      ---> 1100 1111
        assertEquals(str, "11001111");
    }

    // 解码测试
    @Test
    public void decode(){
        String data = "i like like like java do you like a java";
        HuffmanEncoding encode = HuffmanEncodingUtils.encode(data);

        byte[] resultBytes = HuffmanEncodingUtils.decode(encode.codeTable, encode.zipBytes, encode.codeLength);
        assertEquals(new String(resultBytes), data);
    }

    // 压缩测试
    @Test
    public void zipFile(){
        HuffmanEncodingUtils.zipFile("src/test/resources/codeimg/huffmanCode.png", "src/test/resources/codeimg/huffmanCode.zip");
    }

    // 解压测试
    @Test
    public void unzipFile(){
        HuffmanEncodingUtils.unzipFile("src/test/resources/codeimg/huffmanCode.zip", "src/test/resources/codeimg/newCode.png");
    }
}
