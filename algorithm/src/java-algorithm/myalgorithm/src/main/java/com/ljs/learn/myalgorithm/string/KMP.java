package com.ljs.learn.myalgorithm.string;

import java.util.Arrays;

public class KMP {
    public static void main(String[] args) {
        String str1 = "BBCABCDABABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = kmpNext(str2);
        System.out.println(Arrays.toString(next));

        int index = kmpSearch(str1, str2);
        System.out.println(index);
    }

    // 获取一个字符串的前缀表
    public static int[] kmpNext(String dest){
        // 创建一个长度与 dest 相同的整型数组
        int[] next = new int[dest.length()];
        // 第一个字符的匹配值默认为0
        next[0] = 0;

        // i表示当前循环的索引，j表示比较的索引
        // i比j更快
        for(int i=1, j=0;i<dest.length(); i++){
            /*
            【这是KMP算法的核心】
             当i、j的字符不同时，需要重新获取j
             将 j 回溯到与当前字符相等的位置
             因为公共前后缀，无论时从前往后，还是从后往前，都是连续的
             所以 j 就是字符相同位置的索引
             */
            while(j > 0 && dest.charAt(i) != dest.charAt(j)){
                j = next[j-1];
            }

            // 当i、j上的字符相同时，则说明截止到i，出现了公共的前后缀
            // 不停的累加公共的前后缀长度
            if (dest.charAt(i) == dest.charAt(j)){
                j++;
            }

            // 将公共的前后缀长度保存到表中
            next[i] = j;
        }
        return next;
    }

    /** kmp搜索算法
     *
     * @param str1 原字符串
     * @param str2 比较字符串
     * @return 返回第一个匹配的位置，未找到返回-1
     */
    public static int kmpSearch(String str1, String str2){
        // 获取前缀表
        int[] next = kmpNext(str2);

        // i表示 str1 的扫描位置
        // j表示已经匹配成功的字符数量，匹配失败时需要重置
        for (int i=0, j=0; i<str1.length(); i++){
            /*
            【这是KMP算法的核心】
             当 i、j 的字符不想等时，需要重置 j
             */
            while(j > 0 && str1.charAt(i) != str2.charAt(j)){
                j = next[j - 1];
            }

            // 如果两个字符相等，则增加已匹配的字符数量
            if (str1.charAt(i) == str2.charAt(j)){
                j++;
            }

            // 如果已匹配的字符串与str2的长度相等，则说明已经找到了匹配的位置
            if (j == str2.length()){
                return i - j + 1;
            }
        }

        // 如果在for循环中没有匹配成功，则说明未找到
        return -1;
    }
}
