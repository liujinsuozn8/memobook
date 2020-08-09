package com.ljs.learn.myalgorithm.dac;

public class Hanoi {

    /**
     * 移动汉诺塔
     * @param count 圆盘的数量
     * @param a 起始位置
     * @param b 临时存放位置
     * @param c 移动目标
     */
    public static void move(int count, char a, char b, char c) {
        if (count == 1) {
            System.out.println("第" + count + "个，" + a + "-->" +c );
        } else {
            // n >= 2 时
            // 将最上面的盘: A -> B
            move(count - 1, a, c, b);
            // 将最下面的盘: A -> C
            System.out.println("第" + count + "个，" + a + "-->" +c );
            // 将最上面的盘: B -> C
            move(count - 1, b, a, c);
        }
    }
}
