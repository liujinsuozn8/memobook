package com.ljs.learn.myalgorithm.encoding.huffman;

import java.io.*;
import java.util.*;

public class HuffmanEncodingUtils {

    // 编码数据
    public static HuffmanEncoding encode(String data) {
        byte[] bytes = data.getBytes();
        return encode(bytes);
    }

    // 编码数据
    public static HuffmanEncoding encode(byte[] bytes) {
        // 1. 创建结点
        List<HuffmanEncodingNode> nodes = getNodes(bytes);
        // System.out.println(nodes);
        // 2. 构建霍夫曼树
        HuffmanEncodingNode root = createTree(nodes);

        // 3. 创建编码表
        Map<Byte, String> codeTable = createCodeTable(root);

        // 4. 创建编码字符串
        StringBuilder builder = bytesToString(bytes, codeTable);
        // 5. 将编码字符串按照8位拆分成byte数组
        byte[] zipBytes = zip(builder);

        HuffmanEncoding huffmanEncoding = new HuffmanEncoding();
        huffmanEncoding.codeTable = codeTable;
        huffmanEncoding.zipBytes = zipBytes;
        huffmanEncoding.codeLength = builder.length();

        return huffmanEncoding;
    }

    // 1.1 根据数据创建结点
    public static List<HuffmanEncodingNode> getNodes(String data) {
        // 1. 将数据转换为字节数组
        byte[] bytes = data.getBytes();
        return getNodes(bytes);
    }

    // 1.2 根据数据创建结点
    public static List<HuffmanEncodingNode> getNodes(byte[] data) {

        // 2. 统计字符个数
        // data:count
        HashMap<Byte, Integer> map = new HashMap<>();
        Integer count;
        for (byte b : data) {
            count = map.get(b);
            if (count == null) {
                map.put(b, 1);
            } else {
                map.put(b, count + 1);
            }
        }

        // 3. 根据统计结果创建结点
        List<HuffmanEncodingNode> nodes = new ArrayList<>(map.size());
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodes.add(new HuffmanEncodingNode(entry.getValue(), entry.getKey()));
        }

        return nodes;
    }

    // 2. 构建霍夫曼树
    public static HuffmanEncodingNode createTree(List<HuffmanEncodingNode> nodes) {
        while (nodes.size() > 1) {
            // 1. 将结点升序排列
            Collections.sort(nodes);

            // 2. 取出权值最小的两颗树
            HuffmanEncodingNode left = nodes.get(0);
            HuffmanEncodingNode right = nodes.get(1);

            // 3. 创建新的树
            HuffmanEncodingNode newTree = new HuffmanEncodingNode(left.weight + right.weight, null);
            newTree.left = left;
            newTree.right = right;

            // 4. 将新的树保存到数组中
            nodes.set(1, newTree);

            // 5. 删除旧的结点
            nodes.remove(0);
        }

        return nodes.get(0);
    }

    public static Map<Byte, String> codeTable = new HashMap<>();

    // 3.1 创建编码表
    public static Map<Byte, String> createCodeTable(HuffmanEncodingNode node) {
        if (node == null) return null;

        createCodeTable(node.left, "0", new StringBuilder());
        createCodeTable(node.right, "1", new StringBuilder());

        return codeTable;
    }

    // 3.2 创建编码表
    public static void createCodeTable(HuffmanEncodingNode node, String code, StringBuilder codeBuf) {
        StringBuilder buf = new StringBuilder(codeBuf);
        buf.append(code);

        if (node != null) {
            if (node.data == null) {
                // 非叶子节点，继续遍历
                createCodeTable(node.left, "0", buf);
                createCodeTable(node.right, "1", buf);
            } else {
                // 叶子结点，将结点保存到编码表中
                codeTable.put(node.data, buf.toString());
            }
        }
    }

    /**
     * 4. 将每个原始字符进行编码，然后组成一个新的编码字符串
     *
     * @param bytes     原始数据的字符数组
     * @param codeTable 编码表
     * @return 编码后组成的编码字符串（由 0，1 组成）
     */
    public static StringBuilder bytesToString(byte[] bytes, Map<Byte, String> codeTable) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(codeTable.get(b));
        }
        return builder;
    }

    /**
     * 5. 将编码字符串按8位拆分成byte数组
     *
     * @param builder 编码后组成的编码字符串（由 0，1 组成）
     * @return 编码后的byte数组
     */
    public static byte[] zip(StringBuilder builder) {
        // 1. 计算编码后的byte数组的长度，并创建数组
        int length = (builder.length() + 7) / 8;
        byte[] result = new byte[length];

        // 2. 从左向右，将每8位转换为一个byte数
        int index = 0;
        String substr;
        for (int i = 0; i < builder.length(); i += 8) {
            // 检查是否越界
            if (i + 8 < builder.length()) {
                substr = builder.substring(i, i + 8);
            } else {
                substr = builder.substring(i, builder.length());
            }

            // 将2进制数转换为10进制
            result[index] = (byte) Integer.parseInt(substr, 2);
            index++;
        }

        return result;
    }

    /**
     * 将byte数转换为一个二进制字符，返回长度为8的字符串
     *
     * @param code 需要转化的二进制数
     * @return 转换后的二进制字符串
     */
    public static String byteToString(byte code) {
        return byteToString(code, 8);
    }

    /**
     * 将byte数转换为一个二进制字符
     *
     * @param code   需要转化的二进制数
     * @param length 转成二进制字符串后需要截取的位数
     * @return 转换后的二进制字符串
     */
    public static String byteToString(byte code, int length) {
        int temp = code;    // 必须要使用int型。补位时，256有9位，会超过byte的容量
        // 1. 补位
        temp |= 256;

        // 2. 将byte数转换为二进制字符串
        String codeStr = Integer.toBinaryString(temp);

        // 3. 截位
        return codeStr.substring(codeStr.length() - length);
    }

    /**
     * 解码数据
     *
     * @param codeTable    编码表
     * @param huffmanBytes 编码后的字节数组
     * @param codeLength   编码后的长度
     * @return 原始字符串的byte数组
     */
    public static byte[] decode(Map<Byte, String> codeTable, byte[] huffmanBytes, int codeLength) {
        // 1. 将 huffmanBytes 解析为对应的二进制字符串
        StringBuilder builder = new StringBuilder();

        // 1.1 将每个字节转换为字符串，默认截取8位
        for (int i = 0; i < huffmanBytes.length - 1; i++) {
            builder.append(byteToString(huffmanBytes[i]));
        }
        // 1.2 将最后一个字节转换为字符串，不需要补高位
        builder.append(byteToString(
                huffmanBytes[huffmanBytes.length - 1],
                codeLength - (huffmanBytes.length - 1) * 8
        ));

        // 2. 反转编码表的 key、value
        Map<String, Byte> decodeTable = new HashMap<>();
        for (Map.Entry<Byte, String> entry : codeTable.entrySet()) {
            decodeTable.put(entry.getValue(), entry.getKey());
        }

        // 3. 解码
        // 3.1 取出所有编码字符串中所有的 0、 1 字符
        char[] codeChars = new char[builder.length()];
        builder.getChars(0, codeChars.length, codeChars, 0);
        // 3.2 创建一个临时集合来保存解码的结果
        List<Byte> chars = new ArrayList<>();

        // 3.4 遍历所有的 0、1 字符，并通过编码表转换为字符。保存在临时集合中
        int start = 0;
        String key;
        for (int i = 0; i < builder.length(); i++) {
            key = builder.substring(start, i + 1);
            if (decodeTable.containsKey(key)) {
                chars.add(decodeTable.get(key));
                start = i + 1;
            }
        }

        // 3.5 从临时集合中到处数据到 byte数组中
        byte[] resultBytes = new byte[chars.size()];
        for (int i = 0; i < resultBytes.length; i++) {
            resultBytes[i] = chars.get(i);
        }

        // 3.6 转换为字符串并返回
        return resultBytes;
    }

    // 压缩文件
    public static void zipFile(String inPath, String zipPath) {
        try(
                FileInputStream is = new FileInputStream(inPath);
                FileOutputStream os = new FileOutputStream(zipPath);
                ObjectOutputStream objOs = new ObjectOutputStream(os);
        ){
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            HuffmanEncoding encodeResult = encode(bytes);
            objOs.writeObject(encodeResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 解压文件
    public static void unzipFile(String zipPath, String outPath){
        try(
                FileInputStream is = new FileInputStream(zipPath);
                ObjectInputStream objIs = new ObjectInputStream(is);
                FileOutputStream os = new FileOutputStream(outPath);
        ){
            HuffmanEncoding encodeResult = (HuffmanEncoding) objIs.readObject();
            byte[] resultCodes = HuffmanEncodingUtils.decode(
                    encodeResult.codeTable,
                    encodeResult.zipBytes,
                    encodeResult.codeLength);
            os.write(resultCodes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
