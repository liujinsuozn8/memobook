package com.ljs.learn.myalgorithm.search;

import org.junit.Test;

import java.util.List;

public class InsertSearchTest {
    // 测试插值查找的基本实现
    @Test
    public void testSearch(){
        // 创建一个有序的升序序列
        int[] array = {1,2,3,4,5,6,7,8,9};
        assert InsertSearch.search(array, 3) == 2;

        assert InsertSearch.search(array, 0) == -1;
    }

    @Test
    public void testSearchFirstVal(){
        // 创建一个有序的升序序列
        int[] array = {1,2,3,4,5,6,7,8,9};
        assert InsertSearch.search(array, 1) == 0;
    }

    // 测试二分查找的扩展实现--查找所有相同的值
    @Test
    public void testSearchAll(){
        int[] array = {1,2,3,4,5,5,5,5,6,7,8,10,11,12};
        List<Integer> idxList = InsertSearch.searchAll(array, 5);
        System.out.println(idxList);
        // 输出: [4, 5, 6, 7]
    }
}
