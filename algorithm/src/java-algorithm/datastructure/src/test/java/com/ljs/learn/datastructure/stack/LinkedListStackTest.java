package com.ljs.learn.datastructure.stack;

import com.ljs.learn.datastructure.stack.linkedstack.LinkedListStack;
import org.junit.Test;

public class LinkedListStackTest {
    @Test
    public void test01(){
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        assert (stack.pop() == null); // 输出: empty stack

        stack.showStack(); // 输出: empty stack

        stack.push(111);
        stack.push(222);
        stack.push(333);
        stack.push(444);

        stack.showStack();
        // 输出:
        // 444
        // 333
        // 222
        // 111
    }
}
