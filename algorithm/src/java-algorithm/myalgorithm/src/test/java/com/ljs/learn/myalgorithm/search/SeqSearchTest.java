package com.ljs.learn.myalgorithm.search;

import org.junit.Test;

public class SeqSearchTest {
    @Test
    public void testSearch(){
        int[] array = {234,543,35,67,32};
        int idx = SeqSearch.search(array, 35);
        assert (idx == 2);
    }
}
