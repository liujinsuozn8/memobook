package com.ljs.learn.myalgorithm.search;

import org.junit.Test;

public class FibonacciSearchTest {
    @Test
    public void testSearch(){
        int[] array = {1, 8, 10,12, 89, 1000, 1234};
        int result = FibonacciSearch.search(array, 89);

        assert result == 4;
    }

    @Test
    public void testLastVal(){
        int[] array = {1, 8, 10,12, 89, 1000, 1234};
        int result = FibonacciSearch.search(array, 1234);

        assert result == 6;
    }

    @Test
    public void testFirstVal(){
        int[] array = {1, 8, 10,12, 89, 1000, 1234};
        int result = FibonacciSearch.search(array, 1);

        assert result == 0;
    }
}
