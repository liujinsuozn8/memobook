package com.ljs.learn.pattern.interpreter.imporve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) throws IOException {
        // 1. 获取表达式
        System.out.println("请输入表达式：");
        String expStr = new BufferedReader(new InputStreamReader(System.in)).readLine();

        // 2. 输入表达式中的参数值
        HashMap<String, Integer> map = new HashMap<>();
        for (char c : expStr.toCharArray()) {
            if (c != '+' && c != '-'){
                System.out.println("请输入" + c + ":");
                String param = new BufferedReader(new InputStreamReader(System.in)).readLine();
                map.put(String.valueOf(c), Integer.valueOf(param));
            }
        }
        System.out.println(map);

        // 3. 创建计算器
        Calculator calculator = new Calculator(expStr);

        // 4. 执行计算
        System.out.println("计算结果：" + calculator.run(map));
    }
}
