package com.ljs.learn.myalgorithm.recursion;

import org.junit.Test;

public class QueenTest {
    @Test
    public void test(){
        Queen queen = new Queen();
        queen.check(0);
        System.out.println("count = " + queen.count);
    }
}
