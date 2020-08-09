package com.ljs.learn.myalgorithm.dac;

import org.junit.Test;

import static org.junit.Assert.*;

public class HanoiTest {

    @Test
    public void move() {
        Hanoi.move(3, 'A','B','C');

        // 第1个，A-->C
        // 第2个，A-->B
        // 第1个，C-->B
        // 第3个，A-->C
        // 第1个，B-->A
        // 第2个，B-->C
        // 第1个，A-->C
    }
}