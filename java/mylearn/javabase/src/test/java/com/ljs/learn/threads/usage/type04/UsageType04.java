package com.ljs.learn.threads.usage.type04;

import java.util.concurrent.*;

public class UsageType04 {
    public static void main(String[] args) {
        // 1. 创建指定数量线程的线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        // 2. 提供Runnable或Callable接口的实现，来执行线程操作
        //执行Runnable
        pool.execute(new Numbers01()); // 输出偶数
        pool.execute(new Numbers02()); // 输出奇数

        //执行Callable
        Future<Integer> submit = pool.submit(new Numbers03());
        try {
            Integer result = submit.get();
            System.out.println("submit result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 3. 关闭线程池
        pool.shutdown();//关闭线程池
    }
}

// 输出偶数
class Numbers01 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
}

// 输出奇数
class Numbers02 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
}

class Numbers03 implements Callable<Integer>{
    private int sum = 0;
    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0){
                sum += i;
            }
        }
        return sum;
    }
}
