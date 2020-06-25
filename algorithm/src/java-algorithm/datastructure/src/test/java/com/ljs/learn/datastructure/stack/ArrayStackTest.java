package com.ljs.learn.datastructure.stack;

import com.ljs.learn.datastructure.stack.arraystack.ArrayStack;
import org.junit.Test;

public class ArrayStackTest {
    @Test
    public void test01(){
        ArrayStack<Integer> stack = new ArrayStack<>(3);
        assert (stack.pop() == null); // 输出: empty stack

        stack.push(111);
        stack.push(222);
        stack.push(333);
        stack.push(444); // 输出: full stack

        stack.showStack();
        // 输出:
        // 333
        // 222
        // 111
    }
}
