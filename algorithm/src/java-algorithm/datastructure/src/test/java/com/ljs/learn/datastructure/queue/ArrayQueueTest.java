package com.ljs.learn.datastructure.queue;

import org.junit.Test;

public class ArrayQueueTest {
    @Test
    public void test01(){
        BaseArrayQueue<Integer> queue = new BaseArrayQueue<>(4);
        queue.add(55);
        queue.add(33);
        queue.add(77);
        queue.add(66);
        queue.add(88);

        System.out.println("----showQueue----");
        queue.showQueue();

        System.out.println("----peek----");
        System.out.println(queue.peek());

        System.out.println("----take----");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());

        // 队列已经失效
        queue.add(111);
        queue.add(222);

        System.out.println("----showQueue----");
        queue.showQueue();
        // ----showQueue----
        // 55
        // 33
        // 77
        // 66
        // ----peek----
        // 55
        // ----take----
        // 55
        // 33
        // 77
        // 66
        // null
    }

    @Test
    public void test02(){
        CycleArrayQueue<Integer> queue = new CycleArrayQueue<>(4);
        queue.add(55);
        queue.add(33);
        queue.add(77);
        queue.add(66);
        queue.add(88);

        System.out.println("----showQueue----");
        queue.showQueue();

        System.out.println("----peek----");
        System.out.println(queue.peek());

        System.out.println("----take----");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());

        queue.add(111);
        queue.add(222);

        System.out.println("----showQueue----");
        queue.showQueue();

        // ----showQueue----
        // 55
        // 33
        // 77
        // 66
        // ----peek----
        // 55
        // ----take----
        // 55
        // 33
        // 77
        // 66
        // null
    }

    @Test
    public void test03(){
        CycleArrayQueue2<Integer> queue = new CycleArrayQueue2<>(4);
        queue.add(55);
        queue.add(33);
        queue.add(77);
        queue.add(66);
        queue.add(88);

        //System.out.println("----showQueue----");
        //queue.showQueue();

        System.out.println("----peek----");
        System.out.println(queue.peek());

        System.out.println("----take----");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());

        queue.add(111);
        queue.add(222);

        //System.out.println("----showQueue----");
        //queue.showQueue();

        // ----showQueue----
        // 55
        // 33
        // 77
        // 66
        // ----peek----
        // 55
        // ----take----
        // 55
        // 33
        // 77
        // 66
        // null
    }
}
