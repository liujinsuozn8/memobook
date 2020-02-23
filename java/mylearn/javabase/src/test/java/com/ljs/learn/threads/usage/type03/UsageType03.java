package com.ljs.learn.threads.usage.type03;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UsageType03 {
    @Test
    public void test01(){
        Sum sum = new Sum();
        FutureTask<Integer> futureTask = new FutureTask<>(sum);

        Thread t = new Thread(futureTask);
        t.start();

        try {
            Integer result = futureTask.get();
            System.out.println("sum = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Sum implements Callable<Integer>{
    private int sum = 0;

    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + " : " + i);
                sum += i;
            }
        }
        return sum;
    }
}